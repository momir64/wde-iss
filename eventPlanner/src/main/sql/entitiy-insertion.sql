-- TABLE INFO (">>" MEANS THE TABLE HAS TEST DATA)
--
-- User tables:
-- >>admin
-- >>guest
-- >>seller
-- >>event_organizer
-- seller_images
-- event_organizer_images
--
-- >>profile
-- >>profile_blocked_users
--
-- guest_favourite_events
-- >>event_organizer_favourite_products
-- >>event_organizer_favourite_services
--
-- >>chat_message
-- >>registration_attempt
-- >>user_report
--
-- Event tables:
-- >>event_type
-- >>eventtype_servicecategory
-- >>eventtype_productcategory
--
-- event
-- event_activity
--
-- product_budget_item
-- service_budget_item
--
-- Products tables:
-- >>product_category
-- >>static_product
-- >>versioned_product
-- >>versioned_product_eventtype
-- versioned_product_images
--
-- Services tables:
-- >>service_category
-- >>static_service
-- >>versioned_service
-- >>versioned_service_eventtype
-- versioned_service_images
--
-- Misc tables:
-- >>listing_review
-- event_review
-- notification

CREATE OR REPLACE FUNCTION truncate_tables() RETURNS void AS $$
DECLARE
    statements CURSOR FOR
        SELECT tablename FROM pg_tables
        WHERE tableowner = 'admin' AND schemaname = 'public';
BEGIN
    FOR stmt IN statements LOOP
            EXECUTE 'TRUNCATE TABLE ' || quote_ident(stmt.tablename) || ' CASCADE;';
        END LOOP;
END;
$$ LANGUAGE plpgsql;
SELECT truncate_tables();

-- PROFILE
INSERT INTO profile (id, are_notifications_muted, email, is_active, is_verified, password)
VALUES
    -- PROFILES OF ADMINS
    ('7a92c4d7-8742-4a25-aee8-750ca987b42e', true, 'john.doe@example.com', true, true, '123'),
    ('b2d2e1f0-06ae-4b0f-9cd0-7b5b31b1e9fe', false, 'alice.lee@example.com', true, true, '123'),

    -- PROFILES OF EVENT ORGANIZERS
    ('3d82e9b8-3d9b-4c7d-b244-1e6725b78456', false, 'jane.smith@example.com', true, true, '123'),
    ('91c1e927-9f79-44f4-b12f-bcbf16b16c6f', true, 'mark.jones@example.com', true, true, '123'),
    ('a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', false, 'susan.brown@example.com', true, true, '123'),
    ('0cd13f4e-f7de-4533-9071-c42b7b3b4d45', true, 'tom.williams@example.com', true, true, '1233'),

    -- PROFILES OF SELLERS
    ('e852c4ff-3d2b-47ea-b1fd-e711cf18b1d7', false, 'emily.davis@example.com', true, true, '123'),
    ('fddde66f-9b84-4bb8-b408-eae5b815ae69', true, 'robert.martin@example.com', true, true, '123'),
    ('db48e7ac-1d35-4d9d-8e09-bf2e86533b91', false, 'lily.martinez@example.com', true, true, '123'),
    ('8bc76c7b-fc9d-469b-a7b1-2d2291d9a9b6', true, 'samuel.jackson@example.com', true, true, '123'),

    -- PROFILES OF GUESTS
    ('9a4531e5-2fda-42bc-8355-d7991bfc8ff4', true, 'charles.white@example.com', true, true, '123'),
    ('0d1f5f7a-6fcf-42d9-82fe-0090a35ea88c', false, 'grace.perez@example.com', true, true, '123'),
    ('679eb520-7b8d-4c3a-b99f-720e6cfb759b', true, 'oliver.harris@example.com', true, true, '123'),
    ('4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf', false, 'mia.rodriguez@example.com', true, true, '123'),
    ('27e8a1b2-4d23-4b6f-b5a5-79a76d6b758e', true, 'jason.wilson@example.com', true, true, '123'),
    ('542c3a1b-ffea-421d-b967-7d45968c6506', false, 'sophia.morris@example.com', true, true, '123'),
    ('05fdc5be-d59b-468e-8466-d951a4a8d457', true, 'daniel.clark@example.com', true, true, '123'),
    ('03b88b74-0797-4f35-b15f-ff2a3c3e5c88', false, 'isabella.wright@example.com', true, true, '123'),
    ('70c7425e-c5f0-44f0-9e9d-44e4087fce62', true, 'ethan.king@example.com', true, true, '123'),
    ('5a72482a-cd36-46e4-8c09-7b2ff1e22071', false, 'madison.green@example.com', true, true, '123');

INSERT INTO profile_blocked_users (profile_id, blocked_user_id)
VALUES
    -- EVENT ORGANIZER ON SELLER
    ('3d82e9b8-3d9b-4c7d-b244-1e6725b78456', 'e852c4ff-3d2b-47ea-b1fd-e711cf18b1d7'),

    -- SELLER ON EVENT ORGANIZER
    ('fddde66f-9b84-4bb8-b408-eae5b815ae69', '91c1e927-9f79-44f4-b12f-bcbf16b16c6f');

    -- NO SENSE TO ADD MORE BLOCKS BECAUSE OTHER FUNCTIONALITY WILL BE BLOCKED

-- USERS

INSERT INTO admin (id, profile_id) VALUES
    ('3570d6ff-6472-42a5-90fc-9b143db9f778', '7a92c4d7-8742-4a25-aee8-750ca987b42e'),
    ('5a5a94d3-443c-4eda-a263-c541647d7b7c', 'b2d2e1f0-06ae-4b0f-9cd0-7b5b31b1e9fe');

INSERT INTO event_organizer (id, profile_id, address, city, name, postal_number, surname, telephone_number)
VALUES
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '123 Main St, Suite 5', 'New York', 'Jane', '10001', 'Smith', '+1 212-555-1234'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '91c1e927-9f79-44f4-b12f-bcbf16b16c6f', '456 Oak Ave', 'Los Angeles', 'Mark', '90001', 'Jones', '+1 323-555-5678'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', '789 Pine Rd', 'Chicago', 'Susan', '60601', 'Brown', '+1 312-555-9101'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '0cd13f4e-f7de-4533-9071-c42b7b3b4d45', '321 Elm Blvd', 'San Francisco', 'Tom', '94101', 'Williams', '+1 415-555-1122');

INSERT INTO seller (id, profile_id, address, city, name, postal_number, surname, telephone_number)
VALUES
    ('2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5', 'e852c4ff-3d2b-47ea-b1fd-e711cf18b1d7', '987 Maple Dr', 'Seattle', 'Emily', '98101', 'Davis', '+1 206-555-3141'),
    ('a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3', 'fddde66f-9b84-4bb8-b408-eae5b815ae69', '654 Birch Blvd', 'Miami', 'Robert', '33101', 'Martin', '+1 305-555-7282'),
    ('6e0c99f4-226f-49fb-bc4b-1f59ff671b95', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', '321 Cedar Ln', 'Boston', 'Lily', '02101', 'Martinez', '+1 617-555-9865'),
    ('c5e2a004-83b0-4f91-9ff2-c235f2166b72', '8bc76c7b-fc9d-469b-a7b1-2d2291d9a9b6', '543 Pinehill St', 'Austin', 'Samuel', '73301', 'Jackson', '+1 512-555-3746');

INSERT INTO guest (id, profile_id)
VALUES
    ('634182f1-9a18-433b-82d8-dad5aa4069f8', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('c633e080-fad0-4195-8a52-688c149700a1', '0d1f5f7a-6fcf-42d9-82fe-0090a35ea88c'),
    ('95a4669b-9ee6-4608-a4d1-ae52da25be36', '679eb520-7b8d-4c3a-b99f-720e6cfb759b'),
    ('0f0e83c6-6764-4c27-bca6-7369aea6acaa', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf'),
    ('4b423147-32cf-4a90-9238-a3a5934aaee9', '27e8a1b2-4d23-4b6f-b5a5-79a76d6b758e'),
    ('97aaf376-4054-417d-8caf-ecbfecb185c3', '542c3a1b-ffea-421d-b967-7d45968c6506'),
    ('f4618423-15ac-4772-96ac-7144e240b584', '05fdc5be-d59b-468e-8466-d951a4a8d457'),
    ('70e77225-b0a3-403c-b900-0f1788c93780', '03b88b74-0797-4f35-b15f-ff2a3c3e5c88'),
    ('d8413682-21f1-4e35-ba8d-276334c9ffab', '70c7425e-c5f0-44f0-9e9d-44e4087fce62'),
    ('e078765f-21ee-49c0-90a4-1377fe1386e5', '5a72482a-cd36-46e4-8c09-7b2ff1e22071');

-- CHAT MESSAGES

INSERT INTO chat_message (id, is_seen, message, time, from_profile_id, to_profile_id)
VALUES
    -- BETWEEN GUEST AND EVENT ORGANIZER (GUEST, charles.white@example.com) (EVENT ORGANIZER, jane.smith@example.com)
    ('ee2ba33e-74c8-4b23-8016-55b1ecdef8f9', true, 'Hey! How have you been?', '2024-11-23T08:30:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('9797b593-84ef-421d-bde7-9fb15f1f534a', true, 'I’ve been good, just busy with work. What about you?', '2024-11-23T08:32:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('4c0f13e7-6bdf-49f5-b028-fea385256a6d', true, 'Same here. Been swamped with projects. Got any plans for the weekend?', '2024-11-23T08:34:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('7db54276-4767-4a05-bdf4-076536d6910b', true, 'Yeah, I’m going hiking on Saturday! Should be fun. What about you?', '2024-11-23T08:35:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('3ee1d615-7512-4b30-9e01-4d22db8cb7ab', true, 'That sounds great! I might just relax at home this time. Haven’t had a lazy weekend in a while.', '2024-11-23T08:37:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('321ae3c4-3f12-473e-9925-eca5dcf42deb', true, 'I get that! Everyone needs some downtime. Have you seen any good shows or movies recently?', '2024-11-23T08:40:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('7c794159-db52-4ce0-a754-eaa15e9bbe09', true, 'I started watching that new thriller series on Netflix. It’s pretty intense.', '2024-11-23T08:42:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('a2f5589c-f524-4d10-bfbf-570670dcb617', true, 'Ooh, sounds interesting! What’s it called?', '2024-11-23T08:44:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('f8c42e0f-3f90-4ae1-8658-4d3d648dd807', true, 'It’s called “The Silent Witness.” Really gripping! You should check it out.', '2024-11-23T08:46:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('42728107-bc12-4823-9195-df6979d8a5fa', true, 'I will! I’ve been looking for something new to watch. Thanks for the recommendation!', '2024-11-23T08:48:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('c98c3cc5-305c-4c24-aaef-e573d845f328', true, 'No problem! Let me know what you think once you start watching it.', '2024-11-23T08:50:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('dc0f9747-2c8c-4924-aa87-fa1592ce6453', true, 'Will do! By the way, have you tried the new restaurant downtown?', '2024-11-23T08:52:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('469fc739-99b5-4672-8686-00e3d059be39', true, 'I haven’t yet. What kind of food do they serve?', '2024-11-23T08:55:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('8f8bbce6-61db-43c0-840f-a65d452147d1', true, 'It’s an Italian place. The pasta there is amazing!', '2024-11-23T08:57:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('ff3bf40c-6f5b-4230-8516-00e1a3250806', true, 'I love Italian food! I’ll have to check it out soon.', '2024-11-23T08:59:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('fb66f9ea-428c-41e9-9cfc-58060b6942dd', true, 'You won’t regret it. The tiramisu is out of this world!', '2024-11-23T09:01:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('81e04a04-8324-4b82-b4b8-f2bb6f8f739a', true, 'Tiramisu? Now I’m definitely sold. I’ll try it next time I’m there.', '2024-11-23T09:03:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('f10cf6a6-db5e-4138-95cd-14de410ec99e', true, 'You’ll love it! Anyway, do you have any exciting trips planned for the holidays?', '2024-11-23T09:05:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('df462078-39bf-49f2-8d66-7c967e8d5514', true, 'I’m thinking of going to the mountains for a few days to unwind. How about you?', '2024-11-23T09:07:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('1928bb0d-2fc4-443e-bf48-b798fab8b8ee', true, 'That sounds amazing. I’m heading to the beach for a week of relaxation.', '2024-11-23T09:09:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('d8b5e4a2-6d27-46fc-806e-6809fc74b6b9', true, 'The beach sounds perfect! Are you going somewhere tropical?', '2024-11-23T09:11:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('3464c8bc-50a0-41d9-839e-9f0465674abd', true, 'Yes, to a small island in the Caribbean. I can’t wait!', '2024-11-23T09:13:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('d52dc504-424f-4738-8f60-4224208c4656', true, 'That sounds like a dream! Take lots of pictures!', '2024-11-23T09:15:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('f576c24a-907e-4ef7-8cd2-69a53a3a01e6', true, 'I definitely will! I’ll send you some when I’m there.', '2024-11-23T09:17:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('882bb87e-3c17-406c-962f-32366f5b44d6', true, 'I’ll look forward to it! Let me know if you need any recommendations for things to do.', '2024-11-23T09:19:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('297759df-84ff-4b15-b8f6-c7f381645693', true, 'Thanks! I’m sure I’ll find plenty to do there. Maybe we can plan a trip together sometime.', '2024-11-23T09:21:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('8037830a-5280-40a9-8260-1847c251fc27', true, 'That would be awesome! We should definitely do that.', '2024-11-23T09:23:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456'),
    ('0a370703-8daf-40be-870c-1773f5de861b', true, 'I’m going to start researching some destinations. Let’s make it happen!', '2024-11-23T09:25:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),

    -- BETWEEN EVENT ORGANIZER AND SELLER (EVENT ORGANIZER susan.brown@example.com) (SELLER, lily.martinez@example.com)
    ('51f8ca6c-1851-4e12-bf9a-e0994fa950fb', true, 'Hey! How are you doing?', '2024-11-23T10:00:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('35965c72-6835-4cd0-9a6d-cdcfa5f6890f', true, 'Hey! I’m doing well, thanks for asking. How about you?', '2024-11-23T10:02:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('8ca62c99-2556-482e-9e66-906fced369da', true, 'I’m good too, just a little tired. Busy week at work.', '2024-11-23T10:04:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('e2319834-2771-4cbe-97b5-6a79892a55a8', true, 'I hear you. It’s been pretty hectic for me too. Got anything fun planned for the weekend?', '2024-11-23T10:06:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('0646f9dc-4ada-4d51-b07c-b89e34f5d2b5', true, 'I’m actually going to a concert on Saturday night. Looking forward to it!', '2024-11-23T10:08:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('2a894bf0-70d1-47af-8fad-539944f2a7d0', true, 'That sounds awesome! Who’s playing?', '2024-11-23T10:10:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('74171528-3ddd-40e0-8694-8f7673d6f11e', true, 'It’s a local indie band. They’re pretty good! I’ve been listening to them a lot lately.', '2024-11-23T10:12:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('ac3e7bcd-389b-4bac-b316-4ef7343d5722', true, 'Nice, I bet it’s going to be a great show. I should check them out sometime.', '2024-11-23T10:14:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('b45e3fe7-f786-4fea-bb53-be59e8eda997', true, 'You totally should! They’re really talented. What about you? Got any plans for the weekend?', '2024-11-23T10:16:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('eb1c0212-8fb1-4539-9846-ed18d875bad4', true, 'I’m going to a friend’s birthday party. Should be fun!', '2024-11-23T10:18:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('58051851-5dfb-4f20-b1c8-381155b4deec', true, 'Nice! That sounds like a blast. What kind of party is it?', '2024-11-23T10:20:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('6bb19501-52af-42db-abd4-eab4a626495f', true, 'It’s a casual get-together with close friends. We’ll probably just hang out, eat, and play some games.', '2024-11-23T10:22:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('3e530c74-8f04-4574-8ad1-d932542c22ec', true, 'Sounds like a lot of fun! What kind of games do you guys play?', '2024-11-23T10:24:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('3e81b26f-8a8c-4f2b-b741-91f957dbc07d', true, 'We usually play board games or card games, sometimes some video games. Everyone loves a good competition!', '2024-11-23T10:26:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('75224460-7445-4d88-85fd-541ed2268766', true, 'That’s awesome! I’ve been really into board games lately too. Got any recommendations?', '2024-11-23T10:28:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('cf555af8-df91-4bb0-b95f-76b19f727075', true, 'If you like strategy, “Catan” is a classic. Or “Ticket to Ride” for something a bit lighter.', '2024-11-23T10:30:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('ded44c69-1baf-42d2-8d5c-00ecb0de2cd1', true, 'I love “Catan”! I’ll have to check out “Ticket to Ride.” Thanks for the tip!', '2024-11-23T10:32:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('e4cf9a34-a413-4ac8-adfd-9d14d342067c', true, 'No problem! I think you’ll really enjoy it. Let me know what you think after you try it out.', '2024-11-23T10:34:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e'),
    ('a6825c50-22fc-4cce-b914-38cc03111a4a', true, 'Will do! I’m sure we’ll play it this weekend. Have a great time at the party!', '2024-11-23T10:36:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91'),
    ('ae101a7e-8503-4f8f-ab1c-1736daf11688', true, 'Thanks, you too! Enjoy the concert!', '2024-11-23T10:38:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e');

-- PRODUCTS

INSERT INTO product_category (id, description, is_deleted, is_pending, name)
VALUES
    ('e5ad2f36-0d76-43dc-9645-531381c5d29c', 'Pending product category 1', false, true, 'Pending product category 1'),
    ('28db7e87-9d76-4ba7-be6e-1bb91e30228b', 'Pending product category 2', false, true, 'Pending product category 2'),
    ('befeb37b-49b3-4a3f-8055-731db9fbfef4', 'Pending product category 3', false, true, 'Pending product category 3'),
    ('fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', 'Fireworks and pyrotechnic displays for events and celebrations.', false, false, 'Fireworks'),
    ('d13a78bc-9256-4e7f-90b5-354e3f7ab5db', 'Variety of alcoholic and non-alcoholic drinks for events and gatherings.', false, false, 'Drinks'),
    ('ae2a31d5-c7d1-4c30-b268-82d129edb3f6', 'Food and snack options for event guests and parties.', false, false, 'Food'),
    ('f3b6fdeb-c684-47ac-b0be-b4173f36d3b7', 'Event decorations including flowers, table settings, and themes.', false, false, 'Decorations'),
    ('1e3939e7-6822-432a-b322-0ab107f8d582', 'Lighting solutions for events, concerts, and outdoor gatherings.', false, false, 'Lighting'),
    ('72ecfc89-6745-4f5b-a7f2-97fd9ad3f890', 'Stage and tent rentals for outdoor and indoor events.', false, false, 'Stage & Tents');

INSERT INTO static_product (static_product_id, product_category_id)
VALUES
    -- FIREWORKS CATEGORY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'),
    ('b19a7c35-3d60-4fe2-a8b2-e8a2c2741b3b', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'),
    ('9271b6be-64bc-4cfe-bf57-dfa4a0cdad44', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'),

    -- DRINKS CATEGORY
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('04fe2f92-7732-43c9-b9d6-2d801f47b0e0', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('7b95be87-e4fd-4a04-b58d-8297c99a4cd1', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('ee3c6987-dfd9-4375-930f-cf14c55126a7', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('b2f0b7fe-803a-4219-8663-5c93c63e3073', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('462f2b19-45ff-4d91-92bc-8f95cfb78f88', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('ab9f56c0-4379-4b8c-9673-5355b4de07ca', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('e8f5c5fa-3963-4f1a-bb0f-bcd9e803625a', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('f6988288-c4d6-4b3a-bae4-1b6b81a4b02f', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'),

    -- FOOD CATEGORY
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('ef58edcf-2fe7-4595-bdf6-9ef029ff4a98', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('15d6c5fa-f00e-41e3-8cc2-84b05ed9bcb5', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('72dbf31f-e7bb-43d5-890d-bde5c83ef03e', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('cb64a27b-28d0-4b99-8241-cedfe014a635', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('a39fc8ad-becf-4d18-b79e-074fa9d6201a', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('6f2fd5b4-69de-4c95-92f2-02d67d81c204', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('d57c1395-694b-4e69-8f01-f6a5b062502b', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('7ffdb1b9-36a7-4d5c-a1d4-d37a4cfa9282', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'),

    -- DECORATION CATEGORY
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'),
    ('c78d7030-5204-4734-b92f-62c9e7040b33', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'),

    -- LIGHTNING CATEGORY
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', '1e3939e7-6822-432a-b322-0ab107f8d582'),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', '1e3939e7-6822-432a-b322-0ab107f8d582'),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', '1e3939e7-6822-432a-b322-0ab107f8d582'),
    ('8c829577-d7d1-4ea5-b983-e21a41307947', '1e3939e7-6822-432a-b322-0ab107f8d582'),
    ('7b5912f7-d29a-4f60-a9c9-9e8d8acbadae', '1e3939e7-6822-432a-b322-0ab107f8d582'),

    -- STAGE & TENTS CATEGORY
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'),
    ('ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'),
    ('9f0d0870-1f5c-4067-b2f5-fb22a2fe03e3', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'),
    ('c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890');


INSERT INTO versioned_product (static_product_id, version, is_active, is_available, is_last_version, is_private, name, price, sale_percentage)
VALUES
    -- FIREWORKS CATEGORY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, false, true, true,  false, 'Fountain Fireworks', 130.00, 0.1),
    ('b19a7c35-3d60-4fe2-a8b2-e8a2c2741b3b', 1, true, true,  true, false, 'Roman Candles', 200.00, 0.2),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 1, false, true, true,  true, 'Firework Shells', 1200.00, 0.15),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 1, true, false, true,  false, 'Sparklers', 50.00, 0.05),
    ('9271b6be-64bc-4cfe-bf57-dfa4a0cdad44', 1, false, true,  true, false, 'Confetti Cannons', 77.00, 0.0),

    -- DRINKS CATEGORY
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, true, true,  true, false, 'Champagne', 930.00, 0.0),
    ('04fe2f92-7732-43c9-b9d6-2d801f47b0e0', 1, true, true,  true, false, 'Cocktail Mixes', 150.00, 0.15),
    ('7b95be87-e4fd-4a04-b58d-8297c99a4cd1', 1, true, true,  true, false, 'Bottled Water', 100.00, 0.0),
    ('ee3c6987-dfd9-4375-930f-cf14c55126a7', 1, true, true,  true, false, 'Fruit Punch', 200.00, 0.1),
    ('b2f0b7fe-803a-4219-8663-5c93c63e3073', 1, true, false, true,  false, 'Beer Kegs', 800.00, 0.3),
    ('462f2b19-45ff-4d91-92bc-8f95cfb78f88', 1, true, true,  true, false, 'Premium Whiskey', 950.00, 0.1),
    ('ab9f56c0-4379-4b8c-9673-5355b4de07ca', 1, true, true,  true, false, 'Margarita Cocktail Mix', 150.00, 0.15),
    ('e8f5c5fa-3963-4f1a-bb0f-bcd9e803625a', 1, true, true,  true, false, 'Red Wine (Cabernet Sauvignon)', 500.00, 0.2),
    ('f6988288-c4d6-4b3a-bae4-1b6b81a4b02f', 1, false, true, true,  false, 'Non-Alcoholic Beer', 200.00, 0.05),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 1, true, true,  true, true, 'Iced Tea with Lemon', 100.00, 0.0),
    ('b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7', 1, true, true,  true, false, 'Sparkling Water', 75.00, 0.1),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 1, true, true,  true, false, 'Frozen Daiquiri Mix', 180.00, 0.05),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 1, true, true,  true, false, 'Prosecco', 300.00, 0.1),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 1, true, true,  true, false, 'Lemonade (Large Batch)', 150.00, 0.0),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 1, true, true,  true, false, 'Classic Mojito Cocktail Mix', 250.00, 0.15),

    -- FOOD CATEGORY
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, true, true,  true, false, 'Finger Foods & Canapés', 250.00, 0.05),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 1, true, false, true,  false, 'Gourmet Sandwich Platters', 350.00, 0.1),
    ('ef58edcf-2fe7-4595-bdf6-9ef029ff4a98', 1, true, true,  true, false, 'BBQ Buffet', 1800.00, 0.15),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 1, true, true,  true, false, 'Chocolate Fountain', 900.00, 0.2),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 1, true, true,  true, false, 'Mini Burgers', 550.00, 0.0),
    ('15d6c5fa-f00e-41e3-8cc2-84b05ed9bcb5', 1, true, true,  true, true, 'Gourmet Pizza Buffet', 950.00, 0.1),
    ('72dbf31f-e7bb-43d5-890d-bde5c83ef03e', 1, true, true,  true, false, 'Sushi Rolls & Nigiri Platter', 1200.00, 0.15),
    ('cb64a27b-28d0-4b99-8241-cedfe014a635', 1, true, true,  true, false, 'Grilled Meat Skewers', 650.00, 0.1),
    ('b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', 1, true, false, true,  false, 'Fresh Seafood Platter', 1300.00, 0.2),
    ('a39fc8ad-becf-4d18-b79e-074fa9d6201a', 1, true, true,  true, false, 'Vegetarian Tacos', 400.00, 0.05),
    ('6f2fd5b4-69de-4c95-92f2-02d67d81c204', 1, false, true, true,  false, 'Mini Quiche Selection', 250.00, 0.1),
    ('d57c1395-694b-4e69-8f01-f6a5b062502b', 1, true, true,  true, false, 'Gourmet Sandwiches Platter', 500.00, 0.1),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 1, true, true,  true, false, 'Assorted Pasta Salad', 350.00, 0.15),
    ('7ffdb1b9-36a7-4d5c-a1d4-d37a4cfa9282', 1, true, true,  true, false, 'Charcuterie Board', 850.00, 0.2),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 1, true, true,  true, true, 'Dessert Buffet', 1500.00, 0.25),

    -- DECORATION CATEGORY
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, true, true,  true, false, 'Flower Arrangements', 250.00, 0.05),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 1, true, true,  true, true, 'Table Centerpieces', 180.00, 0.1),
    ('c78d7030-5204-4734-b92f-62c9e7040b33', 1, true, true,  true, false, 'Event Balloons', 75.00, 0.2),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 1, false, true, true,  false, 'Backdrop Curtains', 400.00, 0.1),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 1, true, true,  true, false, 'Event Banners', 150.00, 0.05),

    -- LIGHTNING CATEGORY
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, true, true,  true, false, 'Spotlight Rental', 500.00, 0.1),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', 1, true, false, true,  false, 'Fairy Lights', 200.00, 0.15),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', 1, true, true,  true, false, 'LED Wall Washers', 1200.00, 0.2),
    ('8c829577-d7d1-4ea5-b983-e21a41307947', 1, true, true,  true, false, 'Outdoor Flood Lights', 350.00, 0.0),
    ('7b5912f7-d29a-4f60-a9c9-9e8d8acbadae', 1, true, true,  true, false, 'String Lights for Tents', 180.00, 0.01),

    -- STAGE & TENTS CATEGORY
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, false, true, true,  false, 'Stage Setup (Small)', 1500.00, 0.0),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', 1, true, true,  true, true, 'Outdoor Canopy Tent', 1200.00, 0.05),
    ('ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed', 1, true, true,  true, false, 'Portable Stage for Events', 2200.0, 0.1),
    ('9f0d0870-1f5c-4067-b2f5-fb22a2fe03e3', 1, true, true,  true, false, 'Event Tent (Large)', 2500.00, 0.2),
    ('c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3', 1, true, true,  true, false, 'Stage Lights & Effects', 600.00, 0.0);

INSERT INTO event_organizer_favourite_products (id, static_product_id)
VALUES
    -- EVENT ORGANIZER FAVOURITE PRODUCTS
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '763b0d82-81de-4a8c-8bba-45c19e688b31'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', 'a3f5b299-1bc5-45ec-bb6e-b564e0d11c94'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', 'ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', 'a39fc8ad-becf-4d18-b79e-074fa9d6201a'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'c78d7030-5204-4734-b92f-62c9e7040b33'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', '6f2fd5b4-69de-4c95-92f2-02d67d81c204'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '327b9456-b9da-42e1-86c3-f4b25f38a0e9'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '74aee64e-e8e5-490a-90c0-991d76c76752'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', 'b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7');

-- SERVICES

INSERT INTO service_category (id, description, is_deleted, is_pending, name)
VALUES
    ('f9963f95-9f22-4320-905d-5d0641bd3335', 'Pending service category 1', false, true, 'Pending service category 1'),
    ('cb03b66c-8ee4-4f4e-be96-e91164ea0f8d', 'Pending service category 2', false, true, 'Pending service category 2'),
    ('574a6ad5-16d0-4b42-9a4a-e39b0673843c', 'Pending service category 3', false, true, 'Pending service category 3'),
    ('a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', 'Music performance by bands, solo artists or DJ-s for events.', false, false, 'Music'),
    ('d46e1f95-8a90-4745-8000-629f412bdbab', 'Event catering services, including food preparation and serving staff.', false, false, 'Catering'),
    ('6b351a75-3061-4d96-8856-d58f1576a568', 'Photography services for events, including group photos and candid shots.', false, false, 'Photography'),
    ('3d5cb7b1-e512-4eae-bcd9-c2954b643b1b', 'Videography services to capture key moments during the event.', false, false, 'Videography'),
    ('f9c3bc34-6316-47a1-b61a-85f842f8a76d', 'Transportation service for guests, including buses, shuttles, and private vehicles.', false, false, 'Guest Transportation'),
    ('3d0107f7-2cfa-4e95-b5b1-0136034602b6', 'Security services including crowd control, event access monitoring, and safety personnel.', false, false, 'Event Security');

INSERT INTO static_service (static_service_id, service_category_id)
VALUES
    -- MUSIC
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('195eb1c5-6fd6-4139-a697-8cd906219525', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('e30780b8-b7a8-4737-ae5e-cd11a9ad29fa', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('a43f5588-d452-4723-a1a1-f5fae353aaab', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('7e0477b8-ae16-4bd1-9596-e4e4372d8c7e', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('f501fea8-7903-4ff0-a3d3-49493282a69e', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),
    ('fe2e0eb8-29fa-448b-aace-8af9ccb101f9', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'),

    -- CATERING
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 'd46e1f95-8a90-4745-8000-629f412bdbab'),
    ('8ec60ce2-d646-43bf-abf1-01e2d6c5c202', 'd46e1f95-8a90-4745-8000-629f412bdbab'),
    ('b965d715-d2f9-4471-a7f3-5dfb592cfe3d', 'd46e1f95-8a90-4745-8000-629f412bdbab'),

    -- PHOTOGRAPHY
    ('82828d99-3ed9-4a71-8c91-ecfe040411a5', '6b351a75-3061-4d96-8856-d58f1576a568'),
    ('ef1c7659-9bad-4225-b28e-f4b5c133ecbf', '6b351a75-3061-4d96-8856-d58f1576a568'),
    ('245cb1b1-336b-4bf3-9b40-73eee616f2de', '6b351a75-3061-4d96-8856-d58f1576a568'),
    ('9447c5d5-3a82-44e2-8fe3-5d836f0eda63', '6b351a75-3061-4d96-8856-d58f1576a568'),
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', '6b351a75-3061-4d96-8856-d58f1576a568'),
    ('10c348f9-e96c-4073-8704-7012f2daa220', '6b351a75-3061-4d96-8856-d58f1576a568'),
    ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', '6b351a75-3061-4d96-8856-d58f1576a568'),

    -- VIDEOGRAPHY
    ('26f74471-8c03-44a0-b200-8796b351f8aa', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'),
    ('888f99a9-a0e5-469c-bc21-2d6bd429b777', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'),
    ('2c358e12-19f2-4a16-a198-ece7c11f7863', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'),
    ('8fb67698-2344-4b1d-950e-478c14f477cd', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'),
    ('f38ad3b7-2b26-4762-9f64-892953ba5207', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'),

    -- GUEST TRANSPORTATION
    ('8d92004c-ce17-4248-ac60-e0a3750bf083', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'),
    ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'),
    ('b008b01e-4f96-4233-873e-77617645c371', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'),
    ('f43f3ffe-66cb-431b-a88a-fe1b41dbbf22', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'),
    ('fb6845b5-78c6-495f-b550-c043c9372dee', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'),

    -- EVENT SECURITY
    ('faa74e7e-797a-44f8-8c80-31c0f7964e78', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'),
    ('0792d0dd-044d-43df-8031-5f9377522502', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'),
    ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'),
    ('a37de399-5404-4afb-b722-07b790f49ecc', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'),
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', '3d0107f7-2cfa-4e95-b5b1-0136034602b6');

INSERT INTO versioned_service (static_service_id, version, cancellation_deadline, description, duration, is_active, is_available, is_last_version, is_confirmation_manual, is_private, name, price, reservation_deadline, sale_percentage)
VALUES
    -- MUSIC
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 15, 'A classic jazz band providing smooth live music perfect for cocktail hours or dinner events.', 120, true, true, true, false, false, 'Classic Jazz Band', 800, 20, 0.05),
    ('195eb1c5-6fd6-4139-a697-8cd906219525', 1, 25, 'Solo pianist performing a repertoire of contemporary and classical music for upscale events.', 90, true, true, true, false, false, 'Solo Pianist', 500, 15, 0.03),
    ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 1, 19, 'Cover band playing hits from the 80s, 90s, and today for weddings, parties, and corporate events.', 180, true, true, true, false, false, 'Cover Band', 1300, 20, 0.04),
    ('e30780b8-b7a8-4737-ae5e-cd11a9ad29fa', 1, 18, 'Upbeat DJ with a wide selection of genres including EDM, pop, and hip-hop for weddings and parties.', 180, true, true, true, false, false, 'Party DJ', 1500, 20, 0.04),
    ('a43f5588-d452-4723-a1a1-f5fae353aaab', 1, 10, 'Acoustic duo providing live folk and indie music, ideal for intimate gatherings.', 75, true, true, true, false, false, 'Acoustic Duo', 600, 12, 0.02),
    ('7e0477b8-ae16-4bd1-9596-e4e4372d8c7e', 1, 30, 'Rock band with high-energy performances, perfect for corporate events or large parties.', 120, true, true, true, false, false, 'Rock Band', 2000, 30, 0.07),
    ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 1, 20, 'Solo violinist performing classical pieces for formal events and ceremonies.', 60, true, true, true, false, false, 'Solo Violinist', 400, 18, 0.01),
    ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 1, 14, 'DJ specializing in house and techno music, suitable for nightclubs and high-energy events.', 180, true, true, true, false, false, 'House DJ', 1800, 15, 0.06),
    ('f501fea8-7903-4ff0-a3d3-49493282a69e', 1, 16, 'Jazz ensemble with a variety of instruments for high-class receptions and dinner events.', 150, true, true, true, false, false, 'Jazz Ensemble', 1200, 25, 0.05),
    ('fe2e0eb8-29fa-448b-aace-8af9ccb101f9', 1, 22, 'Solo guitarist offering a mix of classical and contemporary acoustic guitar for elegant gatherings.', 90, true, true, true, false, false, 'Solo Guitarist', 550, 20, 0.03),

    -- CATERING
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 25, 'Elegant plated dinner service for weddings and formal events.', 120, true, true, true, false, false, 'Plated Dinner Service', 2000, 15, 0.03),
    ('8ec60ce2-d646-43bf-abf1-01e2d6c5c202', 1, 18, 'Casual BBQ catering, perfect for outdoor events and summer parties.', 180, true, true, true, false, false, 'BBQ Catering', 1200, 20, 0.04),
    ('b965d715-d2f9-4471-a7f3-5dfb592cfe3d', 1, 19, 'Breakfast and brunch catering, perfect for morning events and meetings.', 150, true, true, true, false, false, 'Brunch Catering', 1100, 20, 0.04),

    -- PHOTOGRAPHY
    ('82828d99-3ed9-4a71-8c91-ecfe040411a5', 1, 15, 'Wedding photography service capturing candid and posed moments throughout the entire day, including pre-ceremony and reception.', 480, true, true, true, false, false, 'Wedding Photography', 2500, 20, 0.05),
    ('ef1c7659-9bad-4225-b28e-f4b5c133ecbf', 1, 25, 'Corporate event photography, including keynote speeches, networking sessions, and group photos.', 300, true, true, true, false, false, 'Corporate Event Photography', 1500, 15, 0.03),
    ('245cb1b1-336b-4bf3-9b40-73eee616f2de', 1, 18, 'Outdoor portrait photography session, offering a mix of natural lighting and candid poses in scenic locations.', 120, true, true, true, false, false, 'Outdoor Portrait Photography', 600, 20, 0.04),
    ('9447c5d5-3a82-44e2-8fe3-5d836f0eda63', 1, 30, 'Family photography with customized themes and settings for large family gatherings and reunions.', 180, true, true, true, false, false, 'Family Photography', 1200, 30, 0.07),
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 20, 'Event photography for private parties, birthdays, and celebrations, capturing key moments and group shots.', 240, true, true, true, false, false, 'Event Photography', 800, 18, 0.01),
    ('10c348f9-e96c-4073-8704-7012f2daa220', 1, 16, 'Engagement photography with a mix of romantic portraits and candid shots to celebrate the couple.', 120, true, true, true, false, false, 'Engagement Photography', 700, 25, 0.05),
    ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', 1, 22, 'Newborn and maternity photography to capture beautiful moments for families during pregnancy and the early days of life.', 120, true, true, true, false, false, 'Newborn & Maternity Photography', 900, 20, 0.03),

    -- VIDEOGRAPHY
    ('26f74471-8c03-44a0-b200-8796b351f8aa', 1, 15, 'Full-day wedding videography capturing every moment from preparations to the reception, including drone footage.', 480, true, true, true, false, false, 'Wedding Videography', 3000, 20, 0.05),
    ('888f99a9-a0e5-469c-bc21-2d6bd429b777', 1, 25, 'Corporate event videography, covering keynotes, interviews, and networking sessions with professional editing and highlights.', 300, true, true, true, false, false, 'Corporate Event Videography', 1800, 15, 0.03),
    ('2c358e12-19f2-4a16-a198-ece7c11f7863', 1, 18, 'Highlight reel video for birthdays and private parties, showcasing the best moments with a cinematic touch.', 180, true, true, true, false, false, 'Event Highlight Videography', 1200, 20, 0.04),
    ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, 10, 'Live streaming videography for events, including weddings, conferences, or other occasions, with multi-camera setup.', 240, true, true, true, false, false, 'Live Streaming Videography', 1500, 12, 0.02),
    ('f38ad3b7-2b26-4762-9f64-892953ba5207', 1, 22, 'Maternity and baby shower videography, documenting key moments and emotional speeches during the event.', 120, true, true, true, false, false, 'Maternity & Baby Shower Videography', 750, 20, 0.03),

    -- GUEST TRANSPORTATION
    ('8d92004c-ce17-4248-ac60-e0a3750bf083', 1, 15, 'Luxury limousine service for weddings, including red carpet, drinks, and personalized decor.', 180, true, true, true, false, false, 'Wedding Limousine', 1500, 20, 0.05),
    ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 1, 25, 'Shuttle bus service for corporate events, transporting attendees between venues or from hotels to event sites.', 240, true, true, true, false, false, 'Corporate Shuttle Service', 1200, 15, 0.03),
    ('b008b01e-4f96-4233-873e-77617645c371', 1, 18, 'Private car service for VIP guests at events, offering comfortable and discreet transportation in luxury vehicles.', 120, true, true, true, false, false, 'VIP Car Service', 1000, 20, 0.04),
    ('f43f3ffe-66cb-431b-a88a-fe1b41dbbf22', 1, 22, 'Helicopter transport service for exclusive events, offering quick and luxurious travel to venues with a scenic view.', 60, true, true, true, false, false, 'Helicopter Transport Service', 5000, 20, 0.03),
    ('fb6845b5-78c6-495f-b550-c043c9372dee', 1, 19, 'Luxury van rental for private parties, ensuring that guests are comfortably transported in style between event locations.', 180, true, true, true, false, false, 'Luxury Van Rental', 1500, 20, 0.04),

    -- EVENT SECURITY
    ('faa74e7e-797a-44f8-8c80-31c0f7964e78', 1, 15, 'Professional security personnel for weddings, ensuring guest safety and managing crowd control during the event.', 480, true, true, true, false, false, 'Wedding Security', 1200, 20, 0.05),
    ('0792d0dd-044d-43df-8031-5f9377522502', 1, 25, 'Event security for corporate conferences and expos, providing entry control, monitoring, and incident response.', 600, true, true, true, false, false, 'Corporate Event Security', 2500, 15, 0.03),
    ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', 1, 10, 'Crowd management and security for concerts and festivals, ensuring smooth entry, exits, and maintaining order throughout the event.', 600, true, true, true, false, false, 'Concert & Festival Security', 1800, 12, 0.02),
    ('a37de399-5404-4afb-b722-07b790f49ecc', 1, 16, 'Security for luxury events and gala dinners, offering discreet yet effective protection for high-profile venues and attendees.', 180, true, true, true, false, false, 'Gala Event Security', 1200, 25, 0.05),
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 19, 'Bouncer services for clubs, bars, and private events, ensuring safety and managing entrance to maintain a secure atmosphere.', 300, true, true, true, false, false, 'Bouncer Security', 900, 20, 0.04);

INSERT INTO event_organizer_favourite_services (id, static_service_id)
VALUES
    -- EVENT ORGANIZER FAVOURITE SERVICES
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', 'faa74e7e-797a-44f8-8c80-31c0f7964e78'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '82828d99-3ed9-4a71-8c91-ecfe040411a5'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '9447c5d5-3a82-44e2-8fe3-5d836f0eda63'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', 'e30780b8-b7a8-4737-ae5e-cd11a9ad29fa'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '9affc3a2-6ad9-4677-ae6f-dcc8c77d878b'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '3ed7c6fd-155e-48a9-a376-52b9b3b43ad8'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', '10c348f9-e96c-4073-8704-7012f2daa220'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', '9affc3a2-6ad9-4677-ae6f-dcc8c77d878b'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'faa74e7e-797a-44f8-8c80-31c0f7964e78'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '195eb1c5-6fd6-4139-a697-8cd906219525'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', 'f501fea8-7903-4ff0-a3d3-49493282a69e'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '82828d99-3ed9-4a71-8c91-ecfe040411a5');

INSERT INTO listing_review (id, comment, grade, pending_status, event_organizer_id, product_static_product_id, service_static_service_id)
VALUES
    ('bf8b3ec5-0846-47b7-9f3d-e1b70f6a7311', 'The event catering was exceptional, the food was delicious and everyone enjoyed it.', 5, 'APPROVED', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', null),
    ('4ac2e6fb-4f68-4f37-832d-689cc5f4584e', 'The DJ played great music all night! The vibe was amazing and everyone had a great time.', 5, 'APPROVED', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', null, 'e30780b8-b7a8-4737-ae5e-cd11a9ad29fa'),
    ('2df3f27e-716e-4707-bf6d-2fe56a92a08c', 'I hired a photographer for my wedding and the photos turned out stunning. Highly recommend!', 5, 'APPROVED', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'ee3c6987-dfd9-4375-930f-cf14c55126a7', null),
    ('aeb91c71-1392-47bc-974e-f4842cf3859b', 'The videography service was top-notch. The video captured all the best moments of our event.', 5, 'APPROVED', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99', null, '82828d99-3ed9-4a71-8c91-ecfe040411a5'),
    ('da3b58d7-13d5-4b52-a708-d1e0798a4e1d', 'The security team was professional and made sure everything went smoothly during the event. Very reliable.', 4, 'APPROVED', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', null),
    ('b1577d06-6726-48e7-83b2-c7f2d388f07b', 'Transportation services were on time and comfortable. The drivers were polite and helpful.', 5, 'APPROVED', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', null, '888f99a9-a0e5-469c-bc21-2d6bd429b777'),
    ('31a13d0e-cb59-47ea-9e79-e883bdbcd058', 'The venue was amazing and the staff was extremely helpful. Couldn’t have asked for a better place for our event.', 5, 'APPROVED', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', null),
    ('47c07e3e-317b-4b2e-a5b3-b06d3a77f022', 'The catering options were diverse and the service was very friendly. The guests loved everything.', 4, 'APPROVED', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '327b9456-b9da-42e1-86c3-f4b25f38a0e9', null),
    ('1ec51b6a-408d-41b1-aeae-7a07fd25fce0', 'The event planner was so organized and helped us every step of the way. Highly recommend for anyone looking to organize a smooth event.', 5, 'APPROVED', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', null, 'b008b01e-4f96-4233-873e-77617645c371'),
    ('c8a52907-dfdb-41f9-b6f4-2b8482d47d06', 'The band was fantastic. The live performance was energetic and really set the tone for the evening. Very happy with the service.', 5, 'APPROVED', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', 'b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', null);

INSERT INTO registration_attempt (id, time, profile_id)
VALUES
    ('f6bead0f-eaa7-4d50-87fc-12c3a22d8647', '2024-10-15T14:23:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('d18c76e0-0b3f-44f3-9520-6ea24c6742cd', '2024-10-18T09:12:45', '0d1f5f7a-6fcf-42d9-82fe-0090a35ea88c'),
    ('ad8f2c55-b9f7-43f9-bf61-82fc1d3c6d6c', '2024-10-20T16:45:30', '679eb520-7b8d-4c3a-b99f-720e6cfb759b'),
    ('1b4f8a2a-34b4-4268-90f9-72db84b54379', '2024-10-22T11:08:10', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf'),
    ('0d18b7b9-988d-4647-b27e-1fd211cd1392', '2024-10-25T13:35:22', '27e8a1b2-4d23-4b6f-b5a5-79a76d6b758e'),
    ('8e3091c7-5588-47a9-9a5b-1d2b7bb5b10f', '2024-10-27T08:21:40', '542c3a1b-ffea-421d-b967-7d45968c6506'),
    ('5e52037f-b9de-4f58-a397-22a49e3d83d8', '2024-10-29T19:40:55', '05fdc5be-d59b-468e-8466-d951a4a8d457'),
    ('21f2417a-e670-4f2e-bb0a-bd5e33024d98', '2024-10-30T10:02:14', '03b88b74-0797-4f35-b15f-ff2a3c3e5c88'),
    ('5f4389ca-d5f2-4a1f-a4c8-e6609f2b1cf3', '2024-11-01T15:25:48', '70c7425e-c5f0-44f0-9e9d-44e4087fce62'),
    ('ea4b2417-d98f-4969-9fae-c44d1cf92944', '2024-11-03T12:11:30', '5a72482a-cd36-46e4-8c09-7b2ff1e22071');

INSERT INTO user_report (id, ban_start_date_time, report_date_time, status, from_profile_id, to_profile_id)
VALUES
    ('71adf9c5-cd0f-4639-bb99-2f8c5d1f0e65', null, '2024-11-05T10:30:00', 'DECLINED', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf', '0cd13f4e-f7de-4533-9071-c42b7b3b4d45'),
    ('b02d9284-c6a4-44e6-9d73-bcd4fded42c9', '2024-11-10T09:15:00', '2024-11-07T16:45:00', 'APPROVED', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf', '0cd13f4e-f7de-4533-9071-c42b7b3b4d45');

-- EVENTS

INSERT INTO event_type (id, description, is_active, name)
VALUES
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'A formal ceremony where two people are united in marriage, typically with a reception afterward.', true, 'Wedding'),
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'A high-energy electronic music party, often featuring DJs and vibrant light shows.', true, 'Techno Party'),
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'A formal gathering often held to celebrate a milestone or corporate achievement, usually involving speeches and entertainment.', true, 'Corporate Event'),
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'A festive occasion to celebrate the birth of a child, typically with family and close friends.', true, 'Birthday Party'),
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'A social event often hosted outdoors, where guests enjoy food, music, and dancing under the stars.', true, 'Outdoor Festival'),
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'An elegant celebration often associated with the holiday season, featuring gourmet food and dancing.', true, 'Gala Dinner'),
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'A casual or formal celebration of a couple’s engagement, often involving close friends and family.', true, 'Engagement Party'),
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'A themed event with costumes, music, and dance, typically celebrating Halloween or other occasions.', true, 'Costume Party'),
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'A ceremony and celebration marking a major religious or cultural milestone in a person’s life, such as a baptism or bar mitzvah.', true, 'Religious Ceremony');

-- RECOMMENDED CATEGORIES OF PRODUCTS PER EVENT TYPE

INSERT INTO eventtype_productcategory (eventtype_id, productcategory_id)
VALUES
    -- WEDDING
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- TECHNO PARTY
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'), -- STAGE & TENTS

    -- CORPORATE EVENT
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'), -- STAGE & TENTS

    -- BIRTHDAY PARTY
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- OUTDOOR FESTIVAL
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS

    -- GALA DINNER
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- ENGAGEMENT PARTY
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- COSTUME PARTY
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- RELIGIOUS CEREMONY
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'); -- DECORATIONS

-- RECOMMENDED CATEGORIES OF SERVICES PER EVENT TYPE

INSERT INTO eventtype_servicecategory (eventtype_id, servicecategory_id)
VALUES
    -- WEDDING
     ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
     ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
     ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
     ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'), -- VIDEOGRAPHY
     ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'), -- GUEST TRANSPORTATION
     ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

     -- TECHNO PARTY
     ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
     ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

     -- CORPORATE EVENT
     ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
     ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
     ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
     ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'), -- VIDEOGRAPHY
     ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'), -- GUEST TRANSPORTATION
     ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

     -- BIRTHDAY PARTY
     ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
     ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
     ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
     ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

     -- OUTDOOR FESTIVAL
     ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC

     -- GALA DINNER
     ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
     ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
     ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
     ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

     -- ENGAGEMENT PARTY
     ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
     ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
     ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
     ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'), -- GUEST TRANSPORTATION
     ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

     -- COSTUME PARTY
     ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
     ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

     -- RELIGIOUS CEREMONY
     ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
     ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
     ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'); -- EVENT SECURITY

-- ALLOWED CONCRETE PRODUCTS PER EVENT TYPE
-- IN EACH EVENT TYPE, ONE PRODUCT IS ADDED FOR EVERY RECOMMENDED CATEGORY (SPECIFYING EACH PRODUCT ACCORDING TO REAL LIFE NEEDS IS TOO MUCH WORK FOR LITTLE VALUE)
INSERT INTO versioned_product_eventtype (versioned_product_static_product_id, versioned_product_version, eventtype_id)
VALUES
    -- WEDDING
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- DECORATION CATEGORY ('Flower Arrangements')

    -- TECHNO PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- DRINKS CATEGORY ('Champagne')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- LIGHTNING CATEGORY ('Spotlight rental')
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- STAGE & TENTS CATEGORY ('Stage Setup (Small)')

    -- CORPORATE EVENT
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- LIGHTNING CATEGORY ('Spotlight rental')
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- STAGE & TENTS CATEGORY ('Stage Setup (Small)')

    -- BIRTHDAY PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- DECORATION CATEGORY ('Flower Arrangements')

    -- OUTDOOR FESTIVAL
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- FOOD CATEGORY ('Finger Foods & Canapés')

    -- GALA DINNER
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- DECORATION CATEGORY ('Flower Arrangements')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- LIGHTNING CATEGORY ('Spotlight rental')

    -- ENGAGEMENT PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- DECORATION CATEGORY ('Flower Arrangements')

    -- COSTUME PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- DECORATION CATEGORY ('Flower Arrangements')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- LIGHTNING CATEGORY ('Spotlight rental')

    -- RELIGIOUS CEREMONY
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'); -- DECORATION CATEGORY ('Flower Arrangements')

INSERT INTO versioned_service_eventtype (versioned_service_static_service_id, versioned_service_version, eventtype_id)
VALUES
    -- WEDDING
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- VIDEOGRAPHY CATEGORY ('Live Streaming Videography')
    ('b008b01e-4f96-4233-873e-77617645c371', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- GUEST TRANSPORTATION CATEGORY ('VIP Car Service')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- TECHNO PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- CORPORATE EVENT
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- VIDEOGRAPHY CATEGORY ('Live Streaming Videography')
    ('b008b01e-4f96-4233-873e-77617645c371', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- GUEST TRANSPORTATION CATEGORY ('VIP Car Service')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- BIRTHDAY PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- OUTDOOR FESTIVAL
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- MUSIC CATEGORY ('Classic Jazz Band')

    -- GALA DINNER
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- ENGAGEMENT PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('b008b01e-4f96-4233-873e-77617645c371', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- GUEST TRANSPORTATION CATEGORY ('VIP Car Service')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- COSTUME PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- RELIGIOUS CEREMONY
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'); -- EVENT SECURITY CATEGORY ('Bouncer Security')