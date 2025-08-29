<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // 1. Database connection
    $conn = new mysqli("localhost", "root", "", "healthcare_db");

    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // 2. Collect and sanitize form data
    $full_name = trim($_POST['full_name']);
    $email = trim($_POST['email']);
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT); 

    $dob = $_POST['date_of_birth'];
    $gender = $_POST['gender'];
    $phone = trim($_POST['phone']);
    $emergency_contact = trim($_POST['emergency_contact']);
    $address = trim($_POST['address']);

    // 3. Check if email already exists (prevent duplicates)
    $check_user = "SELECT user_id FROM users WHERE username = '$email'";
    $result = $conn->query($check_user);

    if ($result->num_rows > 0) {
        echo "❌ Error: A user with this email already exists.";
    } else {
        // 4. Insert user account
        $role = 'patient';
        $sql_user = "INSERT INTO users (username, password, role, full_name, email)
                     VALUES ('$email', '$password', '$role', '$full_name', '$email')";

        if ($conn->query($sql_user) === TRUE) {
            $user_id = $conn->insert_id;

            // 5. Insert patient details
            $sql_patient = "INSERT INTO patients (user_id, date_of_birth, gender, phone, emergency_contact, address)
                            VALUES ('$user_id', '$dob', '$gender', '$phone', '$emergency_contact', '$address')";

            if ($conn->query($sql_patient) === TRUE) {
                echo "✅ Patient registered successfully!";
            } else {
                echo "❌ Error creating patient record: " . $conn->error;
            }

        } else {
            echo "❌ Error creating user: " . $conn->error;
        }
    }

    $conn->close();
} else {
    echo "❌ Access denied: You must submit the form.";
}
?>
