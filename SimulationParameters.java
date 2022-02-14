
public class SimulationParameters 
{
	public int MinProcessingTime;
	public int MaxProcessingTime;
	public int MinArrivalTime;
	public int MaxArrivalTime;
	public int TMaxSimulation;
	public int NrQueues;
	public int NrClients;
	public String FileName;
	
	public SimulationParameters(int InNrClients, int InNrServers, int InTimeLimit, int InMinArrivalTime, int InMaxArrivalTime, int InMinProcessingTime, int InMaxProcessingTime, String InFileName)
	{
		NrClients = InNrClients;
		NrQueues = InNrServers;
		TMaxSimulation = InTimeLimit;
		MinArrivalTime = InMinArrivalTime;
		MaxArrivalTime = InMaxArrivalTime;
		MinProcessingTime = InMinProcessingTime;
		MaxProcessingTime = InMaxProcessingTime;
		FileName = InFileName;
	}
}
