# SzakiNapló Android Alkalmazás

Android alkalmazás szakemberek részére:
- Új munkák felvétele (ügyfél neve, lakcím, feladat, dátum, időpont)
- Kiszállások és helyszíni felmérések rögzítése
- GPS koordináta, feljegyzések, fotó készítése kamerával
- "Helyben elvégezve" megjelölése vagy fő munkavégzés időpontjának megadása
- Napi naptár nézet a munkák áttekintéséhez

## Automatikus APK build GitHub-on:
1. Hozz létre egy új repository-t a GitHub-on.
2. Töltsd fel ennek a ZIP-nek a tartalmát.
3. A `.github/workflows/build.yml` munkafolyamat automatikusan elindul a `push` eseményre.
4. Ha lefutott, a GitHub szerverén az **Actions** fül alatt a **SzakiNaplo-APK** szekcióból töltheted le az elkészült `app-debug.apk` fájlt!
