# Simple API To CSV Implementation

### Assumes API response returns json payload in the below format:

```
{
  "banks": [
    {
      "name": "JPMorgan Chase",
      "owner": "JPMorgan Chase & Co.",
      "year_founded": 1799,
      "active_user_accounts": 50000000
    },
    {
      "name": "Bank of America",
      "owner": "Bank of America Corporation",
      "year_founded": 1904,
      "active_user_accounts": 45000000
    },
    {
      "name": "Wells Fargo",
      "owner": "Wells Fargo & Company",
      "year_founded": 1852,
      "active_user_accounts": 35000000
    },
    {
      "name": "Citibank",
      "owner": "Citigroup Inc.",
      "year_founded": 1812,
      "active_user_accounts": 25000000
    },
    {
      "name": "U.S. Bank",
      "owner": "U.S. Bancorp",
      "year_founded": 1863,
      "active_user_accounts": 18000000
    }
  ]
}

```

* `/reports` endpoint fetches API data
* `/reports/download` endpoint downloads API data as CSV


ENV
* `api.base-url` - set base url for your API endpoint