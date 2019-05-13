-- BEGIN TEMPLATE
INSERT INTO mailing_service.template (
    id,
    creator_id,
    date_creating,
    header,
    text,
    type
) VALUES (
    'e8083843-81b2-4384-8650-7d85d0c974d8',
    'b9ed2a13-de38-4df0-b70a-92c9baf32494',
    '2019-04-01 08:00:00+03',
    'Поздравляем, %Student name%!',
    'Вы успешно прошли собеседование на участие в Учебном Центре.
Вашим куратором будет %Mentor name%, его заместителем - %Backup name%.
Вам необходимо прийти %Date% в %Time% на встречу по адресу: %Address%.
Если вы согласны на обучение, то перейдите по ссылке: %Accept link%.
Если же нет, то перейдите по ссылке: %Reject link%.
',
    'congratulations'
);
-- END TEMPLATE
