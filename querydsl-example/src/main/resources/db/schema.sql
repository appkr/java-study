CREATE TABLE `address` (
                           `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                           `addr_code` varchar(25) NOT NULL COMMENT '도로명 주소 관리번호',
                           `road_hash` varchar(32) NOT NULL COMMENT '도로명 해시',
                           `road_addr` varchar(255) NOT NULL COMMENT '도로명 주소',
                           `sido` varchar(20) NOT NULL COMMENT '시도',
                           `sigungu` varchar(20) NOT NULL COMMENT '시군구',
                           `emd` varchar(20) NOT NULL COMMENT '읍면',
                           `road_name` varchar(80) NOT NULL COMMENT '도로명',
                           `road_code` varchar(12) NOT NULL COMMENT '도로명코드',
                           `is_basement` varchar(2) NOT NULL COMMENT '지하여부',
                           `building_no` varchar(11) NOT NULL COMMENT '건물번호',
                           `building_name` varchar(40) NOT NULL COMMENT '건물명',
                           `legal_hash` varchar(32) NOT NULL COMMENT '법정동 해시',
                           `legal_addr` varchar(255) NOT NULL COMMENT '법정동 주소',
                           `legal_dong` varchar(20) NOT NULL COMMENT '법정읍면동',
                           `legal_code` varchar(10) NOT NULL COMMENT '법정동코드',
                           `ri` varchar(20) NOT NULL COMMENT '법정리',
                           `is_mountain` varchar(1) NOT NULL COMMENT '산여부',
                           `beonji` varchar(9) NOT NULL COMMENT '번지',
                           `admin_hash` varchar(32) NOT NULL COMMENT '행정동 해시',
                           `admin_addr` varchar(255) NOT NULL COMMENT '행정동 주소',
                           `admin_dong` varchar(20) NOT NULL COMMENT '행정읍면동',
                           `admin_code` varchar(10) NOT NULL COMMENT '행정동코드',
                           `zip_code` varchar(5) NOT NULL COMMENT '우편번호',
                           `x` varchar(20) NOT NULL COMMENT 'WGS84 x center',
                           `y` varchar(20) NOT NULL COMMENT 'WGS84 y center',
                           `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'updated_at',
                           PRIMARY KEY (`id`),
                           KEY `IDX_ADMIN_HASH` (`admin_hash`),
                           KEY `IDX_LEGAL_HASH` (`legal_hash`),
                           KEY `IDX_ROAD_HASH` (`road_hash`),
                           KEY `IDX_ADDR_CODE` (`addr_code`),
                           KEY `IDX_SIDO_SIGUNGU_EMD_ROAD_NAME_ROAD_CODE` (`sido`,`sigungu`,`emd`,`road_name`,`road_code`),
                           KEY `IDX_SIDO_SIGUNGU_LEGALDONG_RI_LEGALCODE` (`sido`,`sigungu`,`legal_dong`,`ri`,`legal_code`),
                           KEY `IDX_SIDO_SIGUNGU_ADMINDONG_ADMINCODE` (`sido`,`sigungu`,`admin_dong`,`admin_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `coordinates` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `legal_code` varchar(10) NOT NULL COMMENT '주소관할읍면동코드; 시군구코드(5)+읍면동코드(3)+00',
                               `sido` varchar(40) NOT NULL COMMENT '시도명',
                               `sigungu` varchar(40) NOT NULL COMMENT '시군구명',
                               `emd` varchar(40) NOT NULL COMMENT '읍면동명',
                               `road_code` varchar(12) NOT NULL COMMENT '도로명코드; 시군구코드(5)+도로명번호(7)',
                               `road_name` varchar(80) NOT NULL COMMENT '도로명',
                               `is_basement` varchar(1) NOT NULL COMMENT '지하여부; 0:지상, 1:지하, 2:공중',
                               `main_no` varchar(5) NOT NULL COMMENT '건물본번',
                               `sub_no` varchar(5) NOT NULL COMMENT '건물부번',
                               `zip_code` varchar(5) NOT NULL COMMENT '우편번호',
                               `addr_code` varchar(25) NOT NULL COMMENT '건물관리번호; PK',
                               `sigungu_building_name` varchar(40) NOT NULL COMMENT '시군구용건물명',
                               `building_type` varchar(100) NOT NULL COMMENT '건축물용도분류',
                               `admin_code` varchar(10) NOT NULL COMMENT '행정동코드',
                               `admin_dong` varchar(40) NOT NULL COMMENT '행정동명',
                               `floors` varchar(3) NOT NULL COMMENT '지상층수',
                               `sub_floors` varchar(3) NOT NULL COMMENT '지하층수',
                               `is_public` varchar(1) NOT NULL COMMENT '공동주택구분; 0 : 비공동주택, 1 : 공동주택(아파트), 2 : 공동주택(연립/다세대 등)',
                               `building_count` varchar(10) NOT NULL COMMENT '건물수; 같은 주소를 갖는 건물의 수(건물군의 경우 동일한 값)',
                               `detail_building_name` varchar(100) NOT NULL COMMENT '상세건물명',
                               `building_name_change_history` varchar(1000) NOT NULL COMMENT '건물명변경이력; 건물명이 변경된 경우 최초부터 최종변경 건물명을 나열',
                               `detail_building_name_change_history` varchar(1000) NOT NULL COMMENT '상세건물명변경이력; 상세건물명이 변경된 경우 최초부터 최종변경 상세건물명을 나열',
                               `has_habitant` varchar(1) NOT NULL COMMENT '거주여부; 0 : 비거주, 1 : 거주',
                               `x_center` varchar(20) NOT NULL COMMENT '건물중심점_x좌표 ; GRS80 UTM-K 좌표계',
                               `y_center` varchar(20) NOT NULL COMMENT '건물중심점_y좌표; GRS80 UTM-K 좌표계',
                               `x_front` varchar(20) NOT NULL COMMENT '출입구_x좌표; GRS80 UTM-K 좌표계',
                               `y_front` varchar(20) NOT NULL COMMENT '출입구_y좌표; GRS80 UTM-K 좌표계',
                               `wgs84_x_center` varchar(20) NOT NULL COMMENT 'WGS84 x',
                               `wgs84_y_center` varchar(20) NOT NULL COMMENT 'WGS84 y',
                               `sido_eng` varchar(40) NOT NULL COMMENT '시도명(영문)',
                               `sigungu_eng` varchar(40) NOT NULL COMMENT '시군구명(영문)',
                               `emd_eng` varchar(40) NOT NULL COMMENT '읍면동명(영문)',
                               `road_name_eng` varchar(80) NOT NULL COMMENT '도로명(영문)',
                               `is_dong` varchar(1) NOT NULL COMMENT '읍면동구분; 0 : 읍면, 1 : 동',
                               `changed_reason` varchar(2) NOT NULL COMMENT '이동사유코드; 31 : 신규, 34 : 변경, 63 : 삭제',
                               `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated_at',
                               PRIMARY KEY (`id`),
                               KEY `IDX_ADDR_CODE` (`addr_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='jusogokr_road_coords';
