--
-- Database: `stu_examples`
--

-- --------------------------------------------------------

--
-- Table structure for table `give_lecture`
--

CREATE TABLE IF NOT EXISTS `give_lecture` (
  `professor_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`professor_id`,`exam_id`),
  KEY `exam_id` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `attend`
--

CREATE TABLE IF NOT EXISTS `attend` (
  `student_id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`lecture_id`),
  KEY `lecture_id` (`lecture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `is_tutor`
--

CREATE TABLE IF NOT EXISTS `is_tutor` (
  `student_id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`lecture_id`),
  KEY `lecture_id` (`lecture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `lecture`
--

CREATE TABLE IF NOT EXISTS `lecture` (
  `id` int(11) NOT NULL,
  `professor_id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `sws` int(11) NOT NULL,
  `ects` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `professor_id` (`professor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `professor`
--

CREATE TABLE IF NOT EXISTS `professor` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `title` varchar(80) NOT NULL,
  `faculty` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE IF NOT EXISTS `exam` (
  `id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  `lecture_type` varchar(20) NOT NULL,
  `point_in_time` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lecture_id` (`lecture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `participate`
--

CREATE TABLE IF NOT EXISTS `participate` (
  `student_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`exam_id`),
  KEY `exam_id` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `student_number` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `degree_course` varchar(80) NOT NULL,
  `semester` int(80) NOT NULL,
  `enrolled_since` date NOT NULL,
  PRIMARY KEY (`student_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `give_lecture`
--
ALTER TABLE `give_lecture`
  ADD CONSTRAINT `give_lecture_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`),
  ADD CONSTRAINT `give_lecture_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`);

--
-- Constraints for table `attend`
--
ALTER TABLE `attend`
  ADD CONSTRAINT `attend_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_number`),
  ADD CONSTRAINT `attend_ibfk_2` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`);

--
-- Constraints for table `is_tutor`
--
ALTER TABLE `is_tutor`
  ADD CONSTRAINT `is_tutor_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_number`),
  ADD CONSTRAINT `is_tutor_ibfk_2` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`);

--
-- Constraints for table `lecture`
--
ALTER TABLE `lecture`
  ADD CONSTRAINT `lecture_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`);

--
-- Constraints for table `participate`
--
ALTER TABLE `participate`
  ADD CONSTRAINT `participate_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_number`),
  ADD CONSTRAINT `participate_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`);
