# Cryptocurrency Recommendation service

Recommendation service for choosing best cryptocurrency to invest

### Endpoints:
- GET /cryptos
- GET /cryptos/{cryptoSymbol}
- GET /recommendations

#### GET /cryptos
Get a descending sorted list of all the cryptos, comparing the normalized range
###### Return type
[CryptosList](#CryptosList)

#### GET /cryptos/{cryptoSymbol} 
Get cryptocurrency information (oldest/newest/min/max values) by cryptocurrency symbol (cryptoSymbol)

###### Path parameters:
    cryptoSymbol (required)
    The symbol of cryptocurrency that needs to be fetched
###### Responses: 
- 200 successful operation, return type: [CryptoInfo](#CryptoInfo)
- 404 error if cryptocurrency not found, with message "Requested cryptocurrency not found" 
#### GET /recommendations
Get the crypto with the highest normalized range for a specific day (getHighestRangeCryptoForDate)
###### Query parameters
    date (required)
    The date in YYYY-MM-DD format for which the crypto with the highest normalized range 
    is to be retrieved
###### Responses:
- 200 successful operation, return type: [CryptoInfo](#CryptoInfo)
- 404 error if no data for specified date, with message "No information about this date"
 

### Models

#### CryptoInfo

- **symbol**: Symbol of cryptocurrency
- **name**: Name of cryptocurrency
- **description**: Description of cryptocurrency
- **oldestDateTime**: Oldest date-time of cryptocurrency in the period 
- **newestDateTime**: Newest date-time of cryptocurrency in the period
- **oldestPrice**: Oldest price of cryptocurrency in the period
- **newestPrice** Newest price of cryptocurrency in the period
- **minPrice** Minimal price of cryptocurrency in the period
- **maxPrice** Maximal price of cryptocurrency in the period
- **normalizedRange** Normalized range in the period ((max-min)/min)

#### CryptosList

array of [CryptosListItems](#CryptosListItem)

#### CryptosListItem
-  **symbol**: Symbol of cryptocurrency
-  **name**: Name of cryptocurrency
-  **normalizedRange**: Normalized range