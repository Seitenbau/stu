--
-- Database: `stu_examples`
--

-- --------------------------------------------------------

--
-- Table structure for table `GIVE_LECTURE`
--

CREATE TABLE IF NOT EXISTS `GIVE_LECTURE` (
  `professor_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`professor_id`,`exam_id`),
  KEY `exam_id` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `ATTEND`
--

CREATE TABLE IF NOT EXISTS `ATTEND` (
  `student_id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`lecture_id`),
  KEY `lecture_id` (`lecture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `IS_TUTOR`
--

CREATE TABLE IF NOT EXISTS `IS_TUTOR` (
  `student_id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`lecture_id`),
  KEY `lecture_id` (`lecture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `LECTURE`
--

CREATE TABLE IF NOT EXISTS `LECTURE` (
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
-- Table structure for table `PROFESSOR`
--

CREATE TABLE IF NOT EXISTS `PROFESSOR` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `title` varchar(80) NOT NULL,
  `faculty` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `EXAM`
--

CREATE TABLE IF NOT EXISTS `EXAM` (
  `id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  `lecture_type` varchar(20) NOT NULL,
  `point_in_time` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lecture_id` (`lecture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `PARTICIPATE`
--

CREATE TABLE IF NOT EXISTS `PARTICIPATE` (
  `student_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`exam_id`),
  KEY `exam_id` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `STUDENT`
--

CREATE TABLE IF NOT EXISTS `STUDENT` (
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
-- Constraints for table `GIVE_LECTURE`
--
ALTER TABLE `GIVE_LECTURE`
  ADD CONSTRAINT `give_lecture_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `PROFESSOR` (`id`),
  ADD CONSTRAINT `give_lecture_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `EXAM` (`id`);

--
-- Constraints for table `ATTEND`
--
ALTER TABLE `ATTEND`
  ADD CONSTRAINT `attend_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `STUDENT` (`student_number`),
  ADD CONSTRAINT `attend_ibfk_2` FOREIGN KEY (`lecture_id`) REFERENCES `LECTURE` (`id`);

--
-- Constraints for table `IS_TUTOR`
--
ALTER TABLE `IS_TUTOR`
  ADD CONSTRAINT `is_tutor_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `STUDENT` (`student_number`),
  ADD CONSTRAINT `is_tutor_ibfk_2` FOREIGN KEY (`lecture_id`) REFERENCES `LECTURE` (`id`);

--
-- Constraints for table `LECTURE`
--
ALTER TABLE `LECTURE`
  ADD CONSTRAINT `lecture_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `PROFESSOR` (`id`);

--
-- Constraints for table `PARTICIPATE`
--
ALTER TABLE `PARTICIPATE`
  ADD CONSTRAINT `participate_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `STUDENT` (`student_number`),
  ADD CONSTRAINT `participate_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `EXAM` (`id`);
