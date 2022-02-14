import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {

	private BlockingQueue<Client> ClientsQueue;
	private AtomicInteger TotalWaitingTime;
	private int CurrentProcessingTime;
	private List<Client> CurrentAndIncomingClients;
	private HashMap<Client, Integer> ClientsWaitingTime;

	public Queue() {
		ClientsQueue = new LinkedBlockingQueue<Client>();
		CurrentAndIncomingClients = new ArrayList<Client>();
		ClientsWaitingTime = new HashMap<Client, Integer>();

		TotalWaitingTime = new AtomicInteger();
	}

	public void AddClient(Client NewClient) {
		try {
			ClientsQueue.put(NewClient);
			CurrentAndIncomingClients.add(NewClient);
			ClientsWaitingTime.put(NewClient, 0);
			TotalWaitingTime.getAndAdd(NewClient.GetProcessingTime());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				//Daca am terminat de procesat sau suntem la inceput
				if (CurrentProcessingTime == 0) 
				{

					if (CurrentAndIncomingClients.size() != 0) {
					// Am terminat de procesat clientul
						CurrentAndIncomingClients.remove(0);
					}

					//Luam client din Queue
					Client CurrentClient = ClientsQueue.take();
					CurrentProcessingTime = CurrentClient.GetProcessingTime();
				} 
				
				//Crestem timpul de asteptare a clientilor ce asteapta in coada
				IncrementClientsWaitingTime();
				Thread.sleep(1000);
				CurrentProcessingTime--;
				TotalWaitingTime.getAndDecrement();
			}

		} catch (InterruptedException e) {
			System.out.println("Server interrupted");
			return;
		}
	}
	
	private void IncrementClientsWaitingTime()
	{
		for (int i = 1; i < CurrentAndIncomingClients.size(); i++) {
			Client CurrentClient = CurrentAndIncomingClients.get(i);
			ClientsWaitingTime.put(CurrentClient, ClientsWaitingTime.get(CurrentClient) + 1);
		}
	}

	public Client[] GetClients() {
		return CurrentAndIncomingClients.toArray(new Client[ClientsQueue.size()]);
	}

	public int GetWaitingPeriod() {
		return TotalWaitingTime.get();
	}

	public List<Integer> GetWaitingTimes() {
		List<Integer> Times = new ArrayList<Integer>(ClientsWaitingTime.size());
		for (Integer CurrentTime : ClientsWaitingTime.values()) {
			Times.add(CurrentTime);
		}
		return Times;
	}

}
