/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function DemoCountriesData() {
    
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: '/data/countries.json',
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        var countries = [];

        $.each(data, function (key, index) {

            var country = {};
            
            country['countryName'] = index.name;
            country['iso2'] = index.alpha2Code;
            country['iso3'] = index.alpha3Code;
            country['isoNum'] = index.numericCode;
            country['tld'] = index.topLevelDomain[0];
            country['dialCode'] = index.callingCodes[0];
            country['capital'] = index.capital;
            country['flag'] = index.flag;
               
            countries.push(country);
        });

        $.ajax({
                            
            type: "POST",
            contentType: "application/json",
            url: "/api/contacts/countries/update",
            data: JSON.stringify(countries),
            dataType: "json",
            timeout: 60000,
            success: function (count) {

                console.log(JSON.stringify(countries));
                
                if (count === 1) {
                    
                    swal({

                        title: "Success",
                        text: "Countries data successfully updated!",
                        type: "success"

                    });
                    
                } else {
                    
                    swal({

                        title: "Error",
                        text: "Countries database already up to date!",
                        type: "error"
                    });
                }
            }
        });
    });
};
