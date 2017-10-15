package pl.vrp.executor;

import java.util.concurrent.Executor;

public class CallbackExecutor
    implements Executor
{
    public void execute(final Runnable command)
    {
        final Thread runner = new Thread(command);
        runner.start();
        if ((command instanceof CallbackRunnable))
        {
          Thread callerBacker = new Thread(new Runnable()
          {
            public void run()
            {
                try
                {
                  runner.join();
                  ((CallbackRunnable)command).callback();
                }
                catch (Exception e)
                {
                  e.printStackTrace();
                }
            }
          });
          callerBacker.start();
        }
    }
}
