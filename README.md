[![Java CI with Maven](https://github.com/Insomn1ac/job4j_cars/actions/workflows/maven.yml/badge.svg)](https://github.com/Insomn1ac/job4j_cars/actions/workflows/maven.yml)


<a name="title"><h2>Приложение Cars</h2></a>

Web-приложение для продажи машин.

<a name="tech"><h2>Стек используемых технологий</h2></a>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)

<a name="tech"><h2>Скриншоты приложения</h2></a>

Страница авторизации пользователя в приложении:
![authorizationPage](src/main/resources/attachments/authorizationPage.png)

Страница регистрации нового аккаунта в приложении:
![registrationPage](src/main/resources/attachments/registrationPage.png)

Страница со всеми объявлениями:
![allAds](src/main/resources/attachments/allAdvertisements.png)
Таблица с объявлениями сортируема по колонкам (по умолчанию - сортировка по дате, затем по цене). 
Каждое объявление кликабельно.
Объявления могут быть с фото или без.
В приложении есть фильтры объявлений только с фотографиями и только за прошедшие сутки,
также есть поиск по марке автомобиля.

Страница отдельного объявления:
![advertisement](src/main/resources/attachments/advertisement.png)
Возможность продать автомобиль (нажать кнопку "продано") имеется только у аккаунта-создателя этого объявления.
В этом случае статус автомобиля меняется на "Машина продана".
Также только у создателя есть возможность отредактировать или удалить объявление.
При наведении на фото курсора - фото увеличивается:
![adPhotoOnMouseOver](src/main/resources/attachments/adPhotoOnMouseOver.png)

Страница редактирования объявления:
![updateAdvertisement](src/main/resources/attachments/updateAd.png)

Страница добавления нового объявления:
![newAd](src/main/resources/attachments/newAdWithBrandFromList.png)
Если марки автомобиля нет в выпадающем списке - при переходе по ссылке открывается новое окно,
где можно добавить свою марку, тип кузова и двигателя:
![customAd](src/main/resources/attachments/newCustomAd.png)

Страница с объявлениями только пользователя текущей сессии:
![userAds](src/main/resources/attachments/userAds.png)

Страница, на которой собраны только объявления с фото:
![adsWithPhoto](src/main/resources/attachments/adsWithPhoto.png)

Страница с объявлениями за прошедшие сутки:
![pastDayAds](src/main/resources/attachments/pastDayAds.png)

Страница с поиском объявлений:
![successSearch](src/main/resources/attachments/searchByCarBrandPage.png)
Поиск case-insensitive, искать можно по марке/модели 
(к примеру "toyota" и "corolla" выдадут одинаковый список объявлений)
Если в запросе указана марка, которой нет в объявлениях - вернется страница с таким сообщением:
![failedSearch](src/main/resources/attachments/failedSearch.png)
