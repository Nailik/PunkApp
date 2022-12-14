package com.example.punkapp.backend

import kotlin.math.roundToInt

object BeerColorCodes {

    fun getSrmColorCode(srm: Double): Triple<Int, Int, Int>? {
        val index: Int = (srm * 10).roundToInt()
        return if (index < srmColorCodes.size && index >= 0) {
            srmColorCodes[index]
        } else if (index >= 0) {
            srmColorCodes[srmColorCodes.lastIndex]
        } else {
            null
        }
    }

    //https://homebrewacademy.com/srm-color/ conversion of colors
    //srm color codes from https://www.barleydogbrewery.com/xml/colors.xml
    private val srmColorCodes = listOf(
        Triple(248, 248, 230),
        Triple(248, 248, 220),
        Triple(247, 247, 199),
        Triple(244, 249, 185),
        Triple(247, 249, 180),
        Triple(248, 249, 178),
        Triple(244, 246, 169),
        Triple(245, 247, 166),
        Triple(246, 247, 156),
        Triple(243, 249, 147),
        Triple(246, 248, 141),
        Triple(246, 249, 136),
        Triple(245, 250, 128),
        Triple(246, 249, 121),
        Triple(248, 249, 114),
        Triple(243, 249, 104),
        Triple(246, 248, 107),
        Triple(248, 247, 99),
        Triple(245, 247, 92),
        Triple(248, 247, 83),
        Triple(244, 248, 72),
        Triple(248, 247, 73),
        Triple(246, 247, 62),
        Triple(241, 248, 53),
        Triple(244, 247, 48),
        Triple(246, 249, 40),
        Triple(243, 249, 34),
        Triple(245, 247, 30),
        Triple(248, 245, 22),
        Triple(246, 245, 19),
        Triple(244, 242, 22),
        Triple(244, 240, 21),
        Triple(243, 242, 19),
        Triple(244, 238, 24),
        Triple(244, 237, 29),
        Triple(238, 233, 22),
        Triple(240, 233, 23),
        Triple(238, 231, 25),
        Triple(234, 230, 21),
        Triple(236, 230, 26),
        Triple(230, 225, 24),
        Triple(232, 225, 25),
        Triple(230, 221, 27),
        Triple(224, 218, 23),
        Triple(229, 216, 31),
        Triple(229, 214, 30),
        Triple(223, 213, 26),
        Triple(226, 213, 28),
        Triple(223, 209, 29),
        Triple(224, 208, 27),
        Triple(224, 204, 32),
        Triple(221, 204, 33),
        Triple(220, 203, 29),
        Triple(218, 200, 32),
        Triple(220, 197, 34),
        Triple(218, 196, 41),
        Triple(217, 194, 43),
        Triple(216, 192, 39),
        Triple(213, 190, 37),
        Triple(213, 188, 38),
        Triple(212, 184, 39),
        Triple(214, 183, 43),
        Triple(213, 180, 45),
        Triple(210, 179, 41),
        Triple(208, 178, 42),
        Triple(208, 176, 46),
        Triple(204, 172, 48),
        Triple(204, 172, 52),
        Triple(205, 170, 55),
        Triple(201, 167, 50),
        Triple(202, 167, 52),
        Triple(201, 166, 51),
        Triple(199, 162, 54),
        Triple(198, 160, 56),
        Triple(200, 158, 60),
        Triple(194, 156, 54),
        Triple(196, 155, 54),
        Triple(198, 151, 60),
        Triple(193, 150, 60),
        Triple(191, 146, 59),
        Triple(190, 147, 57),
        Triple(190, 147, 59),
        Triple(190, 145, 60),
        Triple(186, 148, 56),
        Triple(190, 145, 58),
        Triple(193, 145, 59),
        Triple(190, 145, 58),
        Triple(191, 143, 59),
        Triple(191, 141, 61),
        Triple(190, 140, 58),
        Triple(192, 140, 61),
        Triple(193, 138, 62),
        Triple(192, 137, 59),
        Triple(193, 136, 59),
        Triple(195, 135, 63),
        Triple(191, 136, 58),
        Triple(191, 134, 67),
        Triple(193, 131, 67),
        Triple(190, 130, 58),
        Triple(191, 129, 58),
        Triple(191, 131, 57),
        Triple(191, 129, 58),
        Triple(191, 129, 58),
        Triple(190, 129, 55),
        Triple(191, 127, 59),
        Triple(194, 126, 59),
        Triple(188, 128, 54),
        Triple(190, 124, 55),
        Triple(193, 122, 55),
        Triple(190, 124, 55),
        Triple(194, 121, 59),
        Triple(193, 120, 56),
        Triple(190, 119, 52),
        Triple(182, 117, 54),
        Triple(196, 116, 59),
        Triple(191, 118, 56),
        Triple(190, 116, 57),
        Triple(191, 115, 58),
        Triple(189, 115, 56),
        Triple(191, 113, 56),
        Triple(191, 113, 53),
        Triple(188, 112, 57),
        Triple(190, 112, 55),
        Triple(184, 110, 52),
        Triple(188, 109, 55),
        Triple(189, 109, 55),
        Triple(186, 106, 50),
        Triple(190, 103, 52),
        Triple(189, 104, 54),
        Triple(188, 103, 51),
        Triple(188, 103, 51),
        Triple(186, 101, 51),
        Triple(186, 102, 56),
        Triple(185, 100, 56),
        Triple(185, 98, 59),
        Triple(183, 98, 54),
        Triple(181, 100, 53),
        Triple(182, 97, 55),
        Triple(177, 97, 51),
        Triple(178, 96, 51),
        Triple(176, 96, 49),
        Triple(177, 96, 55),
        Triple(178, 95, 55),
        Triple(171, 94, 55),
        Triple(171, 92, 56),
        Triple(172, 93, 59),
        Triple(168, 92, 55),
        Triple(169, 90, 54),
        Triple(168, 88, 57),
        Triple(165, 89, 54),
        Triple(166, 88, 54),
        Triple(165, 88, 58),
        Triple(161, 88, 52),
        Triple(163, 85, 55),
        Triple(160, 86, 56),
        Triple(158, 85, 57),
        Triple(158, 86, 54),
        Triple(159, 84, 57),
        Triple(156, 83, 53),
        Triple(152, 83, 54),
        Triple(150, 83, 55),
        Triple(150, 81, 56),
        Triple(146, 81, 56),
        Triple(147, 79, 54),
        Triple(147, 79, 55),
        Triple(146, 78, 54),
        Triple(142, 77, 51),
        Triple(143, 79, 53),
        Triple(142, 77, 54),
        Triple(141, 76, 50),
        Triple(140, 75, 50),
        Triple(138, 73, 49),
        Triple(135, 70, 45),
        Triple(136, 71, 49),
        Triple(140, 72, 49),
        Triple(128, 70, 45),
        Triple(129, 71, 46),
        Triple(130, 69, 47),
        Triple(123, 69, 45),
        Triple(124, 69, 45),
        Triple(121, 66, 40),
        Triple(120, 67, 40),
        Triple(119, 64, 38),
        Triple(116, 63, 34),
        Triple(120, 63, 35),
        Triple(120, 62, 37),
        Triple(112, 63, 35),
        Triple(111, 62, 36),
        Triple(109, 60, 34),
        Triple(107, 58, 30),
        Triple(106, 57, 31),
        Triple(107, 56, 31),
        Triple(105, 56, 28),
        Triple(105, 56, 28),
        Triple(104, 52, 31),
        Triple(102, 53, 27),
        Triple(100, 53, 26),
        Triple(99, 52, 25),
        Triple(93, 53, 24),
        Triple(93, 52, 26),
        Triple(89, 49, 20),
        Triple(90, 50, 21),
        Triple(91, 48, 20),
        Triple(83, 48, 15),
        Triple(88, 48, 17),
        Triple(86, 46, 17),
        Triple(81, 45, 15),
        Triple(83, 44, 15),
        Triple(81, 45, 15),
        Triple(78, 42, 12),
        Triple(77, 43, 12),
        Triple(75, 41, 12),
        Triple(74, 41, 5),
        Triple(78, 40, 23),
        Triple(83, 43, 46),
        Triple(78, 43, 41),
        Triple(78, 40, 41),
        Triple(76, 41, 41),
        Triple(74, 39, 39),
        Triple(74, 39, 39),
        Triple(69, 39, 35),
        Triple(70, 37, 37),
        Triple(68, 38, 36),
        Triple(64, 35, 34),
        Triple(64, 35, 34),
        Triple(62, 33, 32),
        Triple(58, 33, 31),
        Triple(61, 33, 31),
        Triple(58, 33, 33),
        Triple(54, 31, 27),
        Triple(52, 29, 28),
        Triple(52, 29, 28),
        Triple(49, 28, 27),
        Triple(48, 27, 26),
        Triple(48, 27, 26),
        Triple(44, 25, 25),
        Triple(44, 25, 23),
        Triple(42, 24, 26),
        Triple(40, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 22),
        Triple(38, 23, 24),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(25, 16, 15),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(18, 13, 12),
        Triple(17, 13, 10),
        Triple(18, 13, 12),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(16, 11, 10),
        Triple(14, 9, 8),
        Triple(15, 10, 9),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(14, 9, 8),
        Triple(15, 11, 8),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(12, 9, 7),
        Triple(10, 7, 7),
        Triple(10, 7, 5),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(8, 7, 7),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 6, 6),
        Triple(7, 7, 4),
        Triple(6, 6, 3),
        Triple(6, 5, 5),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(4, 5, 4),
        Triple(3, 4, 3),
        Triple(4, 5, 4),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3),
        Triple(3, 4, 3)
    )

}