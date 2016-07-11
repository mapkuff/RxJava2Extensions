/*
 * Copyright 2016 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.akarnokd.rxjava2.math;

import hu.akarnokd.rxjava2.util.DeferredScalarObserver;
import io.reactivex.*;
import io.reactivex.internal.operators.observable.ObservableSource;

public class ObservableSumDouble extends ObservableSource<Double, Double> {

    public ObservableSumDouble(ObservableConsumable<Double> source) {
        super(source);
    }

    @Override
    protected void subscribeActual(Observer<? super Double> observer) {
        source.subscribe(new SumDoubleObserver(observer));
    }
    
    static final class SumDoubleObserver extends DeferredScalarObserver<Double, Double> {

        double accumulator;
        
        public SumDoubleObserver(Observer<? super Double> actual) {
            super(actual);
        }

        @Override
        public void onNext(Double value) {
            if (!hasValue) {
                hasValue = true;
            }
            accumulator += value.doubleValue();
        }
        
        @Override
        public void onComplete() {
            if (hasValue) {
                complete(accumulator);
            } else {
                actual.onComplete();
            }
        }

    }
}
