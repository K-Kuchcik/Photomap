# Photomap
Projekt aplikacji mobilnej fotomapy

PhotoMap to aplikacja, która pozwala tworzyć „fotopunkty” na mapie. Użytkownik robi zdjęcie, a aplikacja automatycznie pobiera aktualną lokalizację GPS, odczytuje metadane EXIF (np. Make/Model/DateTime/Orientation) i zapisuje komplet informacji w lokalnej bazie danych (Room). Zapisane wpisy są wizualizowane jako markery na mapie.

Po kliknięciu w marker użytkownik przechodzi do ekranu szczegółów, gdzie widzi zdjęcie, współrzędne GPS, datę zapisu oraz dane EXIF. Aplikacja umożliwia usunięcie pojedynczego wpisu lub wyczyszczenie wszystkich zapisów („Usuń wszystko”). Interfejs jest zrealizowany w Jetpack Compose z nawigacją między ekranami przez Navigation Compose, a stan UI aktualizuje się reaktywnie na podstawie danych z bazy (StateFlow).

Wymagane uprawnienia

CAMERA – wykonywanie zdjęć

ACCESS_FINE_LOCATION – pobieranie GPS

READ_MEDIA_IMAGES – dostęp do zdjęć
