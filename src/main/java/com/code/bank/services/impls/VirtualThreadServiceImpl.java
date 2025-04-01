package com.code.bank.services.impls;

import com.code.bank.services.interfaces.VirtualThreadService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class VirtualThreadServiceImpl implements VirtualThreadService {

    private final ExecutorService executorService;

    public VirtualThreadServiceImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void runVirtualThreadsTest() {
        for (int i = 0; i < 1000000; i++) {
            executorService.submit(() -> {
                System.out.println("Running in thread: " + Thread.currentThread());
                try {
                    Thread.sleep(1000); // Giả lập xử lý lâu
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
