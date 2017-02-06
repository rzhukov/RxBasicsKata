package org.sergiiz.rxkata;

import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import io.reactivex.Observable;
import io.reactivex.Single;

class CountriesServiceSolved implements CountriesService {

    private static final int ONE_MILLION = 1000000;

    @Override
    public Single<String> countryNameInCapitals(Country country) {
        return Single.just(country.name.toUpperCase());
    }

    public Single<Integer> countCountries(List<Country> countries) {
        return Single.just(countries.size());
    }

    public Observable<Long> listPopulationOfEachCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(country -> country.population);
    }

    @Override
    public Observable<String> listNameOfEachCountry(List<Country> countries) {
        return Observable
                .fromIterable(countries)
                .map(country -> country.name);
    }

    @Override
    public Observable<Country> listOnly3rdAnd4thCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .take(4)
                .takeLast(2);
    }

    @Override
    public Single<Boolean> isAllCountriesPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries)
                .all(country -> country.population > ONE_MILLION);
    }


    @Override
    public Observable<Country> listPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries)
                .filter(country -> country.population > ONE_MILLION);
    }

    @Override
    public Observable<Country> listPopulationMoreThanOneMillion(FutureTask<List<Country>> countriesFromNetwork) {
        return Observable.fromFuture(countriesFromNetwork)
                .flatMap(Observable::fromIterable)
                .filter(country -> country.population > ONE_MILLION);
    }

    @Override
    public Observable<String> getCurrencyUsdIfNotFound(String countryName, List<Country> countries) {
        return Observable.fromIterable(countries)
                .filter(country -> countryName.equals(country.name))
                .map(country -> country.currency)
                .defaultIfEmpty("USD");
    }

    @Override
    public Observable<Long> sumPopulationOfCountries(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(country -> country.population)
                .reduce((aLong, aLong2) -> aLong + aLong2)
                .toObservable();
    }

    @Override
    public Single<Map<String, Long>> mapCountriesToNamePopulation(List<Country> countries) {
        return Observable.fromIterable(countries)
                .toMap(country -> country.name, country -> country.population);
    }
}
