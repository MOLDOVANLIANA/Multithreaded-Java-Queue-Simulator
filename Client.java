
public class Client implements Comparable<Client>{
	private static int StaticId=0;
	private int LocalId;
	private int ArrivalTime;
	private int ProcessingTime;
	
	public Client(int InArrivalTime, int InProcessingTime)
	{
		LocalId = ++StaticId;
		ArrivalTime = InArrivalTime;
		ProcessingTime = InProcessingTime;
	}
	
	public int GetArrivalTime() { return ArrivalTime; }
	public int GetProcessingTime() { return ProcessingTime; }
	public int GetId() { return LocalId; }
	public String GetString()
	{
		return String.format("(%d,%d,%d);", GetId(), GetArrivalTime(), GetProcessingTime());
	}
	
	
	@Override
	public int compareTo(Client o) {
		if(ArrivalTime<o.GetArrivalTime())
			return -1;
		if(ArrivalTime>o.GetArrivalTime())
			return 1;
		
		return 0;
	}
	
}
