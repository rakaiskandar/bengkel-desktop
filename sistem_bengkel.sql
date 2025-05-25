-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2025 at 05:05 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistem_bengkel`
--

-- --------------------------------------------------------

--
-- Table structure for table `spare_parts`
--

CREATE TABLE `spare_parts` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `spare_parts`
--

INSERT INTO `spare_parts` (`id`, `name`, `price`) VALUES
(1, 'Busi (Motor & Mobil)', 95000),
(2, 'Filter Oli (Mobil)', 80000),
(3, 'Filter Udara (Motor)', 40000),
(4, 'Kampas Rem Depan (Mobil)', 300000),
(5, 'Kampas Rem Belakang (Motor)', 75000),
(6, 'Aki/Baterai (Mobil)', 1000000),
(7, 'Aki/Baterai (Motor)', 250000),
(8, 'Lampu Depan/Headlamp (Motor)', 125000),
(9, 'Lampu Depan/Headlamp (Mobil)', 1500000),
(10, 'Oli Mesin 1L (Mobil)', 125000),
(11, 'Oli Mesin 0.8L (Motor)', 75000),
(12, 'Ban Depan (Motor)', 300000),
(13, 'Ban Depan (Mobil)', 800000),
(14, 'Shockbreaker Belakang (Motor)', 600000),
(15, 'Shockbreaker Depan (Mobil)', 1500000),
(16, 'Rantai + Gir Set (Motor)', 300000),
(17, 'Timing Belt (Mobil)', 750000),
(18, 'V-Belt (Motor)', 200000),
(19, 'Radiator (Mobil)', 1500000),
(20, 'CDI Unit (Motor)', 350000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'admin', 'b92fc3dcfd085af699bd871dca5b3f0d2d599434b269095a2280c4344153527b');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `spare_parts`
--
ALTER TABLE `spare_parts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `spare_parts`
--
ALTER TABLE `spare_parts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
