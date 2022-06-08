## ER диаграмма базы данных приложения Filmorate

![ER diagram](https://user-images.githubusercontent.com/45097739/172605582-8a745683-9546-4ea4-b2cf-225da12ecdd2.png)

>friendship_status:\
>0 - заявка на дружбу\
>1 - дружба


### Получение всех фильмов
```
SELECT *
FROM Film;
```    

### Получение всех пользователей
```
SELECT *
FROM User;
```  

### Топ 10 наиболее популярных фильмов

```
SELECT f.name,
		 COUNT(l.user_id) AS likes
FROM Likes AS l
INNER JOIN Film AS f
	ON f.film_id = l.film_id
GROUP BY  f.name
ORDER BY  likes DESC 
LIMIT 10;
```

### Cписок общих друзей с другим пользователем
```
SELECT fr.friend_id
FROM Friends AS fr
WHERE u.user_name = 'Vasya'
		OR u.user_name = 'Petya'
		AND fr.friendship_status = 1
INNER JOIN User AS u
	ON fr.user_id = u.user_id
GROUP BY  fr.friend_id
HAVING COUNT(fr.friend_id)>1;
```
