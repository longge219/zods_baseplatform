package com.zods.mqtt.sever.protocol.channel;
import java.util.function.Consumer;
import java.util.function.Predicate;
/**
 * @description 逻辑操作封装
 * @author jianglong
 * @create 2019-09-09
 **/
public interface BaseApi {

   default  <T> void  doIfElse(T t, Predicate<T> predicate, Consumer<T> consumer){
        if(t!=null){
            if(predicate.test(t)){
                consumer.accept(t);
            }
        }
    }

   default  <T> void  doIfElse(T t, Predicate<T> predicate, Consumer<T> consumer, Consumer<T> consumer2){
        if(t!=null){
            if(predicate.test(t)){
                consumer.accept(t);
            }
            else{
                consumer2.accept(t);
            }
        }
    }
   
    @SuppressWarnings("unchecked")
    default  <T> boolean  doIf(T t, Predicate<T>... predicates){
        if(t!=null){
            for(Predicate<T> p:predicates){
                if(!p.test(t)){
                    return false;
                }
            }
            return true;
        }
        return  false;
    }

    @SuppressWarnings("unchecked")
    default  <T> void   doIfAnd(T t, Consumer<T> consumer2, Predicate<T>... predicates){
        boolean flag =true;
        if(t!=null){
            for(Predicate<T> p:predicates){
                if(!p.test(t)){
                    flag= false;
                    break;
                }
            }
        }
        if(flag){
            consumer2.accept(t);
        }
    }

}
