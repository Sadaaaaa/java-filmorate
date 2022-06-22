package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private final FilmGenreDao filmGenreDao;
	private final JdbcTemplate jdbcTemplate;

//	@Test
//	void contextLoads() {
//	}

	@Test
	public void testUserDbStorage() {
		// вставляем юзера
		String sqlInsert = "INSERT INTO USERS (USER_ID, USER_NAME, USER_LOGIN, USER_EMAIL, USER_BIRTHDAY)" +
				"VALUES (1, 'Alex', 'LEX', 'alexLEX@email.com', '2017-7-28')";
		jdbcTemplate.update(sqlInsert);

		// возвращаем юзера id=1
		Optional<User> userOptional = userStorage.getById(1);

		assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", 1)
				);

		// возвращаем всех юзеров
		Map<Integer, User> allUsers = userStorage.getUsers();
		assertThat(allUsers.size()).isEqualTo(1);
		assertThat(allUsers.get(1)).hasFieldOrPropertyWithValue("name", "Alex");
		assertThat(allUsers.get(1)).hasFieldOrPropertyWithValue("login", "LEX");
		assertThat(allUsers.get(1)).hasFieldOrPropertyWithValue("email", "alexLEX@email.com");
		//assertThat(allUsers.get(1)).hasFieldOrPropertyWithValue("birthday", "2017-7-28");

		// добавляем юзера id=2
		User user = new User("lilu@email.com", "LIL", "Lena", LocalDate.of(2019, 2, 22));
		userStorage.add(2, user);
		allUsers = userStorage.getUsers();
		assertThat(allUsers.size()).isEqualTo(2);
		assertThat(allUsers.get(2)).hasFieldOrPropertyWithValue("name", "Lena");
		assertThat(allUsers.get(2)).hasFieldOrPropertyWithValue("login", "LIL");
		assertThat(allUsers.get(2)).hasFieldOrPropertyWithValue("email", "lilu@email.com");

		// oбновляем юзера id=2
		User userUpd = new User("lild@gmail.com", "LILD", "Liza", LocalDate.of(2019, 2, 22));
		userStorage.update(2, userUpd);
		allUsers = userStorage.getUsers();
		assertThat(allUsers.size()).isEqualTo(2);
		assertThat(allUsers.get(2)).hasFieldOrPropertyWithValue("name", "Liza");
		assertThat(allUsers.get(2)).hasFieldOrPropertyWithValue("login", "LILD");
		assertThat(allUsers.get(2)).hasFieldOrPropertyWithValue("email", "lild@gmail.com");

		// удаляем таблицу
		String sqlDropTable = "DROP TABLE USERS";
		jdbcTemplate.update(sqlDropTable);
	}

	@Test
	public void testFilmDbStorage() {
		// вставляем фильм
		String sqlInsert = "INSERT INTO FILMS (FILM_ID, FILM_NAME, FILM_DESCRIPTION, FILM_RELEASEDATE, FILM_DURATION, FILM_MPAID)" +
				"VALUES (1, 'Dogma', 'Funny film', '1995-1-01', 100, 2)";
		jdbcTemplate.update(sqlInsert);

		// возвращаем фильм id=1
		Optional<Film> filmOptional = filmStorage.getById(1);

		assertThat(filmOptional)
				.isPresent()
				.hasValueSatisfying(film ->
						assertThat(film).hasFieldOrPropertyWithValue("id", 1)
				);

		// возвращаем все фильмы
		Map<Integer, Film> allFilms = filmStorage.getFilms();
		assertThat(allFilms.size()).isEqualTo(1);
		assertThat(allFilms.get(1)).hasFieldOrPropertyWithValue("name", "Dogma");
		assertThat(allFilms.get(1)).hasFieldOrPropertyWithValue("description", "Funny film");
		assertThat(allFilms.get(1)).hasFieldOrPropertyWithValue("duration", 100);
		assertThat(allFilms.get(1)).hasFieldOrProperty("mpa");

		// добавляем фильм id=2
		Film film = new Film(2,"2Fast", "Cars race", LocalDate.of(2019, 2, 22), 150, new Mpa(2));
		filmStorage.add(2, film);
		allFilms = filmStorage.getFilms();
		assertThat(allFilms.size()).isEqualTo(2);
		assertThat(allFilms.get(2)).hasFieldOrPropertyWithValue("name", "2Fast");
		assertThat(allFilms.get(2)).hasFieldOrPropertyWithValue("description", "Cars race");
		assertThat(allFilms.get(2)).hasFieldOrPropertyWithValue("duration", 150);
		assertThat(allFilms.get(2)).hasFieldOrProperty("mpa");

		// обновляем фильм id=2
		Film filmUpd = new Film(2,"LaLaLand", "Dance", LocalDate.of(2019, 2, 22), 100, new Mpa(2));
		filmStorage.update(2, filmUpd);
		allFilms = filmStorage.getFilms();
		assertThat(allFilms.size()).isEqualTo(2);
		assertThat(allFilms.get(2)).hasFieldOrPropertyWithValue("name", "LaLaLand");
		assertThat(allFilms.get(2)).hasFieldOrPropertyWithValue("description", "Dance");
		assertThat(allFilms.get(2)).hasFieldOrPropertyWithValue("duration", 100);
		assertThat(allFilms.get(2)).hasFieldOrProperty("mpa");

		// удаляем фильм id=2
		filmStorage.delete(2);
		allFilms = filmStorage.getFilms();
		assertThat(allFilms.size()).isEqualTo(1);
	}
}
