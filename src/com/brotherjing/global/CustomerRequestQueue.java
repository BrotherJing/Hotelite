package com.brotherjing.global;

import jp.co.worksap.intern.entities.customer.CustomerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class CustomerRequestQueue {

    private static List<CustomerRequest> queue ;

    public static void init(){
        queue = new ArrayList<CustomerRequest>();
    }

    public static void enqueue(CustomerRequest request){
        queue.add(request);
    }

    public static CustomerRequest dequeue(){
        if(queue.isEmpty())return null;
        return queue.remove(0);
    }

    public static CustomerRequest peek(){
        if(queue.isEmpty())return null;
        return queue.get(0);
    }

    public static boolean isEmpty(){
        return queue.isEmpty();
    }

}
