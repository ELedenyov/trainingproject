package by.fertigi.app;

//@SpringBootApplication
//@Import(DbConfig.class)
public class RunnerApp {
    public static void main(String[] args) {
        //ConfigurableApplicationContext context = SpringApplication.run(RunnerApp.class, args);

//        добавление данных в базу
//        FillingDB fillingDB = context.getBean(FillingDB.class);
//        for (int i = 0; i < 15; i++) {
//            fillingDB.doAction();
//        }


//start
//        EntityRepository entityRepository = context.getBean(EntityRepository.class);
//
//        ExecutorService executor = Executors.newFixedThreadPool(10);//Thread amount
//
//        int countRow = entityRepository.countRow();
//        int pool = Runtime.getRuntime().availableProcessors();
//        int step = countRow/1000;
//
//
//        GetCountLimit getCountLimit = new GetCountLimit();
//        getCountLimit.setCount(0);
//        getCountLimit.setStep(step);
//
//        Runnable task = ()->{
//            int count = getCountLimit.getCount();
//            while (count < countRow) {
//                try {
//                    entityRepository.update(count, step);
////                    entityRepository.selectForUpdate(count, step);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                count = getCountLimit.getCount();
//            }
//        };
//
//        for (int i = 0; i < pool; i++) {
//            new Thread(task).start();
//            try {
//                Thread.sleep(15);
//            } catch (InterruptedException e) {
//                System.out.println("exception!!!!");
//            }
//        }
        //finish
    }
}

class GetCountLimit {
    private Integer count = 0;
    private Integer step=0;

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