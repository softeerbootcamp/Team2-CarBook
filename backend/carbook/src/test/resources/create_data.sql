insert into TYPE (id, tag)
values  (5, 'MPV'),
        (2, 'N / N Line'),
        (4, 'SUV'),
        (8, '버스'),
        (6, '소형트럭&택시'),
        (1, '수소 / 전기차'),
        (3, '승용'),
        (7, '트럭');

insert into MODEL (id, type_id, tag)
values  (1, 1, '아이오닉 6'),
        (2, 1, '아이오닉 5'),
        (3, 1, '넥쏘'),
        (4, 1, '포터 2 Electric'),
        (5, 1, '포터 2 Electric 특장'),
        (6, 2, '아반떼 N'),
        (7, 2, '아반떼 N Line'),
        (8, 2, '쏘나타 N Line'),
        (9, 2, '투싼 N Line'),
        (10, 2, '투싼 N Line Hybrid'),
        (11, 3, '디 올 뉴 그랜저'),
        (12, 3, '디 올 뉴 그랜저 Hybrid'),
        (13, 3, '아반떼'),
        (14, 3, '아반떼 Hybrid'),
        (15, 3, '쏘나타'),
        (16, 3, '쏘나타 Hybrid'),
        (17, 4, '디 올 뉴 코나'),
        (18, 4, '디 올 뉴 코나 Hybrid'),
        (19, 4, '베뉴'),
        (20, 4, '투싼'),
        (21, 4, '투싼 Hybrid'),
        (22, 4, '싼타페'),
        (23, 4, '싼타페 Hybrid'),
        (24, 4, '펠리세이드'),
        (25, 5, '스타리아 라운지'),
        (26, 5, '스타리아'),
        (27, 5, '스타리아 라운지 리무진'),
        (28, 5, '스타리아 라운지 캠퍼'),
        (29, 5, '스타리아 킨더'),
        (30, 5, '포터 2 포레스트'),
        (31, 6, '디 올 뉴 그랜저 택시'),
        (32, 6, '쏘나타 택시'),
        (33, 6, '스타리아 라운지 모빌리티'),
        (34, 6, '포터 2'),
        (35, 6, '포터 2 특장차'),
        (36, 7, '마이티'),
        (37, 7, '파비스'),
        (38, 7, '뉴파워트럭'),
        (39, 7, '엑시언트'),
        (40, 7, '엑시언트 수소전기트럭'),
        (41, 8, '쏠라티'),
        (42, 8, '카운티'),
        (43, 8, '카운티 일렉트릭'),
        (44, 8, '에어로타운'),
        (45, 8, '그린시티'),
        (46, 8, '슈퍼에어로시티'),
        (47, 8, '유니시티'),
        (48, 8, '유니버스'),
        (49, 8, '유니버스 모바일 오피스'),
        (50, 8, '일렉시티'),
        (51, 8, '일렉시티 수소전기버스'),
        (52, 8, '일렉시티 이층버스');

insert into HASHTAG (tag)
values  ('테스트태그1'),
        ('테스트태그2'),
        ('테스트태그3');

insert into `USER` (email, password, nickname)
values ('test1@test.com', 'bimil1','testname1'),
       ('test2@test.com', 'bimil2','testname2'),
       ('test3@test.com', 'bimil3','testname3'),
       ('test4@test.com', 'bimil4','testname4');

insert into FOLLOW (follower_id, following_id, is_deleted)
values (1,2,0),
       (1,3,0),
       (1,4,1),
       (2,1,0),
       (2,4,0),
       (4,3,0);

insert into POST (user_id, create_date, update_date, content, model_id, is_deleted, like_count)
values (1,TIMESTAMPADD(DAY, -5, NOW()),'2023-02-07 11:24:24','1번 유저 첫글',1,0,3),
       (1,TIMESTAMPADD(DAY, -6, NOW()),'2023-02-07 11:25:10','1번 유저 두번째글',2,0,0),
       (2,TIMESTAMPADD(DAY, -4, NOW()),'2023-02-07 11:25:20','2번 유저 첫글',3,1,3),
       (2,TIMESTAMPADD(DAY, -3, NOW()),'2023-02-07 11:25:33','2번 유저 두번째글',4,0,2),
       (3,TIMESTAMPADD(DAY, -2, NOW()),'2023-02-07 11:25:35','3번 유저 첫글',5,0,0),
       (3,TIMESTAMPADD(DAY, -1, NOW()),'2023-02-07 11:25:45','3번 유저 두번째글',6,1,0),
       (4,NOW(),'2023-02-07 11:27:33','4번 유저 첫글',7,0,0),
       (4,TIMESTAMPADD(DAY, 1, NOW()),'2023-02-07 11:28:33','4번 유저 두번째글',8,0,0);

insert into IMAGE (post_id, image_url)
values (1, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/1_이미지.jpeg'),
       (2, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/2_이미지.jpeg'),
       (3, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/3_이미지.jpeg'),
       (4, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/4_이미지.jpeg'),
       (5, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/5_이미지.jpeg'),
       (6, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/6_이미지.jpeg'),
       (7, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/7_이미지.jpeg'),
       (8, 'https://team2-carbook.s3.ap-northeast-2.amazonaws.com/images/8_이미지.jpeg');

insert into POST_HASHTAG (post_id, tag_id)
values (1,1),
       (1,2),
       (1,3),
       (2,2),
       (3,3),
       (4,1),
       (7,2),
       (8,3);

insert into POST_LIKE (user_id, post_id, is_deleted)
values (1, 1, 0),
       (1, 3, 0),
       (2, 1, 0),
       (2, 3, 0),
       (3, 1, 0),
       (3, 4, 1),
       (4, 3, 0),
       (4, 4, 0);