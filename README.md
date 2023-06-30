# RadiusOptoinSelection
Showcasing option selection form using API, LocalCache(Realm), and MVVM

<img src="https://github.com/BobFactory/RadiusOptoinSelection/assets/26218176/7f4ed277-561f-476c-90ab-286beae35c10" data-canonical-src="https://github.com/BobFactory/RadiusOptoinSelection/assets/26218176/7f4ed277-561f-476c-90ab-286beae35c10" width="300" height="600" />
<img src="https://github.com/BobFactory/RadiusOptoinSelection/assets/26218176/6e00faa9-87dc-49ff-bf0d-d70bdf1daa41" data-canonical-src="https://github.com/BobFactory/RadiusOptoinSelection/assets/26218176/6e00faa9-87dc-49ff-bf0d-d70bdf1daa41" width="300" height="600" />


## Objective

To showcase a selection form that is derived from the API and also disable options based on user selection. 

- From the API, display the list of facilities and its options by creating a UI, so that users
can select any one option from each facility. Display name and icon.

 - Add the exclusion list, so that users aren't able to select options that don't go well with each other.
For example, users can’t select these options together.
Ex. 1: Property Type: Land, Number of Rooms: 1 to 3 rooms.
Ex. 2: Property Type: Boat House, Other Facilities: Garage

- Use Realm to Persist the data, so every time you load data from the DB, refresh your DB from API once a day.

## Techstack

- Jetpack compose ( UI implementation )
- MVVM with repository pattern ( Architecture pattern )
- Clean code folder organization
- Realm ( Local Data Persistence )
- SharedPreference
- Retrofit ( API transactions )
- Coroutines ( Async programming )
- Hilt ( Dependency management )
- State Hoisting ( There is always a single state that reflects the UI)
- Dynamic UI ( UI rendered based on the API and can be customised )
