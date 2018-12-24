package by.fertigi.app.service;

import org.springframework.stereotype.Service;

@Service
public class GetCountLimit {
    private Integer count = 0;
    private Integer step=0;

    public GetCountLimit() {
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public synchronized Integer getCount() {
        int returnCount = count;
        count = count + step;
        return returnCount;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
