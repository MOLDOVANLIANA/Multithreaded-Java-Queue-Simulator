import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationManager implements Runnable {

	private SimulationParameters Parameters;
	
	private List<Queue> Queues;
	private ExecutorService Executor;
	private List<Client> Clients;
	private SimulationLaunchView View;
	private boolean ShouldStop = false;
	
	public SimulationManager(SimulationParameters InParameters, SimulationLaunchView InView)
	{
		Parameters = InParameters;
		Queues = new ArrayList<Queue>();
		Clients = new ArrayList<Client>();
		View = InView;
		
		Logger.Get().InitializeWriter(InParameters.FileName + ".txt");
			
		Executor = Executors.newFixedThreadPool(InParameters.NrQueues);
		
		for(int i=0; i<InParameters.NrQueues; i++)
		{
			Queue NewQueue = new Queue();
			Queues.add(NewQueue);
			Executor.execute(NewQueue);
		}

		
		GenerateRandomTasks(InParameters.NrClients);		
	}
	

	
	@Override
	public void run() {
		
		int CurrentTime = 0;
		while(CurrentTime <Parameters.TMaxSimulation && !ShouldStop )
		{
			if(Clients.size()!=0)
			{
				for(int i=0;i<Clients.size();i++)
				{
					Client CurrentClient = Clients.get(i); 
					if(CurrentClient.GetArrivalTime() <= CurrentTime)
					{
						Clients.remove(i);
						i--;
						SendClientToQueues(CurrentClient);
					}
				}
							
			}
			
			//Printare in fisier
			Logger.Get().Println("Time "+ CurrentTime);			
			String WaitingClientsText = GetWaitingClientsText();			
			String QueuesText = GetQueuesText();
			Logger.Get().Println(WaitingClientsText);
			Logger.Get().Println(QueuesText);
			
			//Afisare in interfata
			String UiText = WaitingClientsText + "\n" + QueuesText;
			View.SetListContent(Arrays.asList(UiText.split("\n")));
			View.SetDeltaTime(CurrentTime);
			
			//Verificare daca mai sunt clienti care trebuie sa intre in magazin
			//sau mai sunt clienti in procesare
			ShouldStop = true;
			if(Clients.size()!=0)
			{
				ShouldStop = false;
			}
			
			for(Queue CurrentQueue: Queues)
			{
				
				if(CurrentQueue.GetClients().length!=0)
				{
					ShouldStop=false;
					break;					
				}		
			}
			
			try {
				Thread.sleep(1000);
				CurrentTime++;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
		PrintAverageTime();
		ShutDown();

	}
	
	
	private String GetWaitingClientsText()
	{
		String WaitingClientsText = ("Waiting Clients: ");
		if(Clients.size()==0)
			WaitingClientsText+="empty";
		
		for(Client GeneratedClient : Clients)
			WaitingClientsText += GeneratedClient.GetString();
		
		return WaitingClientsText;
	}
	
	
	private String GetQueuesText()
	{
		String QueuesText = new String();
		for(int i =0; i< Queues.size(); i++)
		{
			Queue CurrentQueue = Queues.get(i);
			QueuesText+= GetQueueLineText(CurrentQueue, i);
		}
		return QueuesText;
	}

	private String GetQueueLineText(Queue CurrentServer, int QueueNumber)
	{
		String ClientText = "Queue "+ QueueNumber + ": ";
		
		Client[] ClientsArray = CurrentServer.GetClients();
		if(ClientsArray.length==0)
		{
			ClientText += "closed";
		}
		else
		{
			for(int i = 0; i<ClientsArray.length;i++)
			{
				Client CurrentClient = ClientsArray[i];
				ClientText+= CurrentClient.GetString();
			}
		}
		ClientText += "\n";
		return ClientText;
	}
	
	private void PrintAverageTime()
	{
		List<Integer> Times = new ArrayList<Integer>();
		
		for(Queue CurrentQueue : Queues)
		{
			Times.addAll(CurrentQueue.GetWaitingTimes());
		}
		
		float Sum = 0;
		
		for(Integer Value : Times)
		{
			Sum+=Value;
		}
		float Average = Sum/Times.size();
		Logger.Get().Println("Average Waiting Time: "+ Average);
		
	}
	private void GenerateRandomTasks(int Count)
	{
		for(int i = 0; i<Count; i++)
		{
			int ProcessingTime = 0;
			while(ProcessingTime < Parameters.MinProcessingTime)
				ProcessingTime = ThreadLocalRandom.current().nextInt(Parameters.MaxProcessingTime);				
			
			int ArrivalTime = 0;
			while(ArrivalTime < Parameters.MinArrivalTime)		
				ArrivalTime = ThreadLocalRandom.current().nextInt(Parameters.MaxArrivalTime);
			
			Client NewClient = new Client(ArrivalTime, ProcessingTime);			
			Clients.add(NewClient);
			
		}
		
		Clients.sort(null);		
	}
	
	public void SendClientToQueues(Client C)
	{
		if(Queues.size()>0)
		{
			Queue QueueWithLeastWaitingPeriod = Queues.get(0);
			for(int i=1; i<Queues.size(); i++)
			{
				if(Queues.get(i).GetWaitingPeriod()<QueueWithLeastWaitingPeriod.GetWaitingPeriod())
				{
					QueueWithLeastWaitingPeriod = Queues.get(i);
				}				
			}
			QueueWithLeastWaitingPeriod.AddClient(C);			
		}	
	}
	
	public void ShutDown()
	{
		System.out.println("Scheduler Shutdown Started");
		Executor.shutdownNow();
		while(!Executor.isTerminated());
		System.out.println("Scheduler Shutdown Finished");
	}
	
	
}
