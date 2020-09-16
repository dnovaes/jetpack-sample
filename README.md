# Android Jetpack Sample
This project was made in Kotlin unsing AndroidX for studying purpose and uses the following libs (the major libs are in bold):
- **Koin for dependency injection**
- **Coroutines for multithreading**
- **Retrofit for API requests**
- **Navigation**
- **Databinding**
- **Paging**
- Moshi as Json parser
- Timber
- Glide
- SwipeRefreshLayout
- ConstraintLayout
- Cardview
- Ktx
- **Espresso for instrumented testing**
- **Mockito for unit testing**

## Functionality
This is a single-page app that provides the ability to search Outdoorsy listings by keyword. Users will be able to type keywords in a text field. Typing in the text field will search for relevant results and display them for the user. Listings should display an image of the vehicle and the vehicle’s name.

## Endpoint documentation

*URL:*
GET https://search.outdoorsy.co/rentals

*Query parameters:*
Required: `filters[keywords]` - a space-separated string of search terms
Optional: `page[limit]` - an integer that sets requested maximum result count
Optional: `page[offset]` - an integer that sets the index of the first result
(Example: a “third page” of 8 results would have limit of 8, offset of 16)

*Response:*
The `data` object is an array of rental results.
Find name of rental at `data[].attributes.name`
Find primary image URL of rental by getting the ID from `data[].relationships.primary_image.data.id` and finding the matching `id` with `type: images` from the `included` array. The image URL is at `included[].attributes.url`.

## Notes
- This was built to be maintained for a while.
- The architecture used was MVVM, with DataSources & Repositories.
- The code design and style was intended to be consistent and reasonable

Please ask if you have any questions!
