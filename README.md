# NewsApp
Приложения показывает новости взависимости от региона пользователя. Также присутсвует возможность поиска новостей, и просмотр источника внутри приложения.

Api использованное в приложение:
* https://newsapi.org/
* https://api.openweathermap.org/

Архитектура
=====================

* presentation
  * single activity
  * fragments
  * viewModels
  * ViewPagerAdapter
* domain
  * Usecases
  * NewsRepository
  * Модели
* data
  * NewsRepository - реализация
  * ApiProvider
  * Модели
  * Mappers
  
<img src="https://user-images.githubusercontent.com/105432796/180985245-49a9210b-3e7c-46c4-a3a0-590bbcca9c81.jpg" width="400" />   <img src="https://user-images.githubusercontent.com/105432796/180985250-3c413324-680f-4faa-9a80-6f2df7b7369f.jpg" width="400" />

Use case:
-----------------------------------

* Получить координаты пользователя
* Получить страну и город пользователя по координатам
* Поиск новостей по региону
* Поиск новостей по тэгу
* Запуск WebView источника

Использованные технологии:
=====================
1. Google Play Service Location
2. Picasso
3. Retorfit
4. ViewBinding
