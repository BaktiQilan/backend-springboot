-- Insert sample data into the `Item` table
INSERT INTO item (name, description, price, stock) VALUES
                                                       ('Laptop', 'High performance laptop', 1500.00, 10),
                                                       ('Smartphone', 'Latest model smartphone', 700.00, 25),
                                                       ('Headphones', 'Noise cancelling headphones', 150.00, 50),
                                                       ('Smartwatch', 'Feature-packed smartwatch', 200.00, 30),
                                                       ('Tablet', 'Portable and powerful tablet', 300.00, 20),
                                                       ('Keyboard', 'Mechanical keyboard with backlighting', 120.00, 15),
                                                       ('Mouse', 'Wireless mouse with high precision', 50.00, 40),
                                                       ('Monitor', '27-inch 4K monitor', 400.00, 12),
                                                       ('Printer', 'All-in-one color printer', 180.00, 8),
                                                       ('Webcam', 'HD webcam with built-in microphone', 80.00, 25);



-- Insert sample data into the `User` table
INSERT INTO "user" (username, password) VALUES
                                            ('username', '$2a$12$Fnpdj0ucIVaGfrnXFxYoweOvOHM9955AVc1gOXb72O17KQrwwjTPy'),
                                            ('john_doe', '$2a$12$Fnpdj0ucIVaGfrnXFxYoweOvOHM9955AVc1gOXb72O17KQrwwjTPy'),
                                            ('jane_smith', '$2a$12$Fnpdj0ucIVaGfrnXFxYoweOvOHM9955AVc1gOXb72O17KQrwwjTPy'),
                                            ('alice_jones', '$2a$12$Fnpdj0ucIVaGfrnXFxYoweOvOHM9955AVc1gOXb72O17KQrwwjTPy');