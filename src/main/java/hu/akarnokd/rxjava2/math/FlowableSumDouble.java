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

import org.reactivestreams.*;

import hu.akarnokd.rxjava2.util.DeferredScalarSubscriber;
import io.reactivex.internal.operators.flowable.FlowableSource;

final class FlowableSumDouble extends FlowableSource<Double, Double> {

    public FlowableSumDouble(Publisher<Double> source) {
        super(source);
    }

    @Override
    protected void subscribeActual(Subscriber<? super Double> observer) {
        source.subscribe(new SumDoubleSubscriber(observer));
    }
    
    static final class SumDoubleSubscriber extends DeferredScalarSubscriber<Double, Double> {

        /** */
        private static final long serialVersionUID = 600979972678601618L;

        double accumulator;
        
        public SumDoubleSubscriber(Subscriber<? super Double> actual) {
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
