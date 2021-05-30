This android application fetches exchange rates from: https://currencylayer.com/documentation. At first it fetches total currencies from this website and store the currency list in shared preference. There is a spinner of currency list; user can select a currency and then this app fetches exchange rates for the selected currency from the above mentioned website. User can enter an amount and can select any exchange rate from a recycler view (list of exchange rates). Upon selecting an exchange rate app will convert the amount to the selected currency.

Live currency is stored in shared preference and fetched after every 30 minutes.

Some unit testing code has been included here. Unit testing includes selecting a currency and giving an input to the amount field.

Retrofit and coroutine is used to fetch data from remote server. This whole application is written in kotlin. Espresso is used for unit testing.
