package com.react.task.service;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;


class BackPressureTest {

    @Test
    public void testBackPressure() {
        var numbers = Flux.range(1,100).log();
        //numbers.subscribe(integer -> System.out.println("integer ="+ integer));

        numbers.subscribe(new BaseSubscriber<Integer>() {
            /**
             * Hook for further processing of onSubscribe's Subscription. Implement this method
             * to call {@link #request(long)} as an initial request. Values other than the
             * unbounded {@code Long.MAX_VALUE} imply that you'll also call request in
             * {@link #hookOnNext(Object)}.
             * <p> Defaults to request unbounded Long.MAX_VALUE as in {@link #requestUnbounded()}
             *
             * @param subscription the subscription to optionally process
             */
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(10);
            }

            /**
             * Hook for processing of onNext values. You can call {@link #request(long)} here
             * to further request data from the source {@link Publisher} if
             * the {@link #hookOnSubscribe(Subscription) initial request} wasn't unbounded.
             * <p>Defaults to doing nothing.
             *
             * @param value the emitted value to process
             */
            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value"+value);
                if(value ==3 ) cancel();
            }

            /**
             * Optional hook for completion processing. Defaults to doing nothing.
             */
            @Override
            protected void hookOnComplete() {
                super.hookOnComplete();
            }

            /**
             * Optional hook for error processing. Default is to call
             * {@link Exceptions#errorCallbackNotImplemented(Throwable)}.
             *
             * @param throwable the error to process
             */
            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }
        });
    }

    @Test
    public void testBackPressureDrop() {
        var numbers = Flux.range(1,100).log();
        //numbers.subscribe(integer -> System.out.println("integer ="+ integer));

        numbers
                .onBackpressureDrop(integer -> {
                    System.out.println("number drop "+ integer);
                })
                .subscribe(new BaseSubscriber<Integer>() {
            /**
             * Hook for further processing of onSubscribe's Subscription. Implement this method
             * to call {@link #request(long)} as an initial request. Values other than the
             * unbounded {@code Long.MAX_VALUE} imply that you'll also call request in
             * {@link #hookOnNext(Object)}.
             * <p> Defaults to request unbounded Long.MAX_VALUE as in {@link #requestUnbounded()}
             *
             * @param subscription the subscription to optionally process
             */
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(10);
            }

            /**
             * Hook for processing of onNext values. You can call {@link #request(long)} here
             * to further request data from the source {@link Publisher} if
             * the {@link #hookOnSubscribe(Subscription) initial request} wasn't unbounded.
             * <p>Defaults to doing nothing.
             *
             * @param value the emitted value to process
             */
            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value"+value);
                if(value ==3 ) hookOnCancel();
            }

            /**
             * Optional hook for completion processing. Defaults to doing nothing.
             */
            @Override
            protected void hookOnComplete() {
                super.hookOnComplete();
            }

            /**
             * Optional hook for error processing. Default is to call
             * {@link Exceptions#errorCallbackNotImplemented(Throwable)}.
             *
             * @param throwable the error to process
             */
            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }
        });
    }

    @Test
    public void testBackPressureBuffer() {
        var numbers = Flux.range(1,100).log();
        //numbers.subscribe(integer -> System.out.println("integer ="+ integer));

        numbers
                .onBackpressureBuffer(10 ,
                        i -> System.out.println("Buffered"+i))
                .subscribe(new BaseSubscriber<Integer>() {
                    /**
                     * Hook for further processing of onSubscribe's Subscription. Implement this method
                     * to call {@link #request(long)} as an initial request. Values other than the
                     * unbounded {@code Long.MAX_VALUE} imply that you'll also call request in
                     * {@link #hookOnNext(Object)}.
                     * <p> Defaults to request unbounded Long.MAX_VALUE as in {@link #requestUnbounded()}
                     *
                     * @param subscription the subscription to optionally process
                     */
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    /**
                     * Hook for processing of onNext values. You can call {@link #request(long)} here
                     * to further request data from the source {@link Publisher} if
                     * the {@link #hookOnSubscribe(Subscription) initial request} wasn't unbounded.
                     * <p>Defaults to doing nothing.
                     *
                     * @param value the emitted value to process
                     */
                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value"+value);
                        if(value ==3 ) hookOnCancel();
                    }

                    /**
                     * Optional hook for completion processing. Defaults to doing nothing.
                     */
                    @Override
                    protected void hookOnComplete() {
                        super.hookOnComplete();
                    }

                    /**
                     * Optional hook for error processing. Default is to call
                     * {@link Exceptions#errorCallbackNotImplemented(Throwable)}.
                     *
                     * @param throwable the error to process
                     */
                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }
                });
    }



}
