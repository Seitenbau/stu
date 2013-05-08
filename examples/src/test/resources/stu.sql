--
-- Database: `stu`
--
--
-- Table structure for table `jobs`
--

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` int(11) NOT NULL,
  `title` varchar(80) NOT NULL,
  `description` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

CREATE TABLE IF NOT EXISTS `teams` (
  `id` int(11) NOT NULL,
  `title` varchar(80) NOT NULL,
  `description` varchar(80) NOT NULL,
  `membersize` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Table structure for table `persons`
--

CREATE TABLE IF NOT EXISTS `persons` (
  `id` int(11) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `name` varchar(80) NOT NULL,
  `job_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `job_id` (`job_id`),
  KEY `team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `persons`
  ADD CONSTRAINT `persons_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`),
  ADD CONSTRAINT `persons_ibfk_2` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`);

--
-- --------------------------------------------------------