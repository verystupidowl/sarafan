# **Контрольная точка №1**
### Тема и описание:
Web-приложение мессенджер с постами и комментариями к ним на языках Java с 
использованием Spring Framework и JavaScript с использованием Vue.js. В качестве БД будет использоваться PostgreSQL, 
свзязь с ним будет осуществляться за счет ORM Hibernate, тестирование будет 
производиться библиотекой JUnit 5 (Может быть другая) 
Приложение будет состоять из множества страниц. 
### Примерный функционал:
В разработку планируется локальное 
приложение мессенджер по типу твиттера “SARAFAN”. 
В данном приложении будет присутствовать функционал авторизации пользователя с помощью Google OAuth2. 
Пользователь будет обладать профилем с личными данными, взятыми с его профиля в Google (имя, регион, электронная почта, 
последний визит, количество подписок и подписчиков и его аватарка). На стартовой странице 
пользователь сможет увидеть все посты, оставленные пользователями, на которых он подписан, написать комментарии 
к ним или собственный пост. Также, по нажатию на аватарку или имя пользователя, будет открыт его профиль со всеми 
данными и кнопкой "Подписаться" / "Отписаться". Email пользователя будет виден только после подписки и ее подтверждения. 
Подтвердить подписку можно из соответствующего уведомления или в профиле в разделе "Подписки". Также в любое время в 
этом же разделе можно отменить подтверждение, в таком случае подписчик не увидит его посты. Посты можно удалять и 
редактировать, однако после редактирования около поста будет надпись "изменено" с датой последнего изменения.
В разделе "Настройки" можно включить/отключить уведомления на определенные действия 
(В будущем возможно функционал увеличится) из действий: новый подписчик, новый пост, и новый комментарий под постом 
пользователя. При нажатии на логотип приложения открывается список всех пользователей, зарегистрированных в приложении.
В тестовой части планируется протестировать процесс авторизации пользователя, создание поста, комментариев и подписок.
### План проекта:
1.	Подготовка описания проекта и его основных задач - 20.10.22
2.	Сценарии использования системы с бизнес-целями, бизнес-задачами и пользовательскими сценариями - 27.10.22
3.	Описание архитектуры системы - 31.10.22
4.	План тестирования системы - 3.11.22
5.	Реализация приложения - 15.11.22
6.	Результаты тестирования системы - 17.11.22
7. Инструкции пользователя системы - 1.12.22
8. Оформление отчёта - 1.12.22

# **Контрольная точка №2**
### Бизнес-цели и задачи:
#### BG-1: 
Увеличение числа постов на платформе.
#### F1-1: 
Предоставление пользователю возможности выкладывать посты.
#### UC1-1-1:
1. Пользователь переходя на страницу сайта может авторизоваться с помощью своего аккаунта Google.
2. После успешной регистрации пользователю предоставляются возможность написать и опубликовать пост.
3. После публикации своего поста, он отображается у всех подписчиков пользователя.
4. Пользователь имеет возможность изменить или удалить свой пост.
#### F1-2 Предоставление пользователю возможности просматривать посты других юзеров
#### UC1-1-2:
1. Пользователь заходит на главную страницу и ему предоставляется список всех постов пользователей, на которых он подписан.
2. Пользователь может написать комментарий к посту.
3. При открытии профиля юзера, пользователь видит все данные и может на него подписаться/отписаться.
#### F2-1:
Возможность частично настроить приложение.
#### UC2-1-1:
1. Пользователь зоходит в меню настроек
2. Пользователь имеет возможность решать, какие категории уведомлений будут ему приходить.
### Архитектура приложения:
Архитектура приложения Приложение будет соответствовать базовой архитектуре построения программы на SPRING и 
будет состоять из 8 основных пакетов(config , controllers , domain , services , repositories, dto, exceptions, utils). 
Приложение будет взаимодействовать с базой данных PostgreSQL. Сборщиком проекта выступает Maven. 
В пакете configurations описана основная реализация Spring Security , а именно обозначена страница авторизации , 
определены юрл для авторизованных пользователей и для залогированных . В пакете controllers 
Написаны основные классы определяющие методы и их юрл пути для взаимодействия с приложением . Логика данных методов 
будет описана в пакете services . В нем Используя данные из пакета repositories путем внедрения зависимости будет 
реализована основная логика методов которые работают с базой данных. В пакете domain будут храниться основные 
сущности(comment , message , user) связи между которыми будут осуществлены с помощью аннотаций Spring . 
Frontend будет реализован через JavaScript с помощью фреймворка Vue.js. Он будет состоять из 6 основных пакетов (api, components, pages, router, store, utils).
Пакет api будет содержать файлы js для связи с бэкендом. components будет содержать vue-файлы компоненты, которые могут
повторно использоваться. Все страницы будут содержаться в пакете pages. В пакете route будет лежать js файл, отвечающий
за многостраничность. Папка store будет содержать файлы, описывающие изменение состояния основных объектов и связи с
бэкендом с помощью файлов из папки api. Папка utils будет содержать файлы конфигурации веб-сокетов.
Свзяь между ними будет реализована с помощью архитектурного стиля REST API. 
### План тестирования:
Планируется протестировать Добавление нового пользователя в базу данных (как успешное добавление , так и неудачное).