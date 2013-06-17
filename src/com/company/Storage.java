package com.company;
import java.io.*;

public class Storage {
	 
    /**
     * Двумерный массив с задачами.
     * Ключ записи - <i> задачи.
     * Поля записи - <a> старта и финиша, имя задачи
     */
    public String[][] tasks;
 
    
    public Storage() {
		this.tasks = new String[100][];
		
		//this.tasks[0] = new String[]{"Название задачи", "2013-10-10 11:12:13", "2013-10-10 11:12:14"};
    }
    
    
    /**
     * Добавление задачи
     */
 
	public String[] add(String[] task) {
		
		String[][] data = getTasks();
		
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] == null)
			{
				data[i] = task;
				
				break;
			}
		}
		
		store(data);
		
		return  task;
	}
 
    /**
     * воpвращает задачу по айдишнику
     */
	public String[] get(Integer id) {
	
		String[][] data = getTasks();
		
	    return  data[id];
	}
    
    /**
     * воpвращает id задачи по имени,
     * если нет задачи с заданным именем -1
     */
	public int getIdByName(String name) {
		
		String[][] data = getTasks();
		
		for(int i = 0; i < data.length; i++)
		{
			if(data[i][0] == name)
				return i;
		}
		
		return -1;
	}
    
    /**
     *  удаление задачи по id
     */
    public boolean delete(Integer id) {
        
    	String[][] data = getTasks();
    	
    	tasks[id] = new String[]{};
    	
    	store(data);
    	
        return true;
    }
    
    /**
     *  удаление всех задач
     */
    public boolean truncate() {
    	
    	store(new String[100][]);
      	
        return true;
    }
    
    
    /**
     *  загрузка данных
     */
    private String[][] getTasks() {

    	try{
			FileInputStream fis = new FileInputStream("storage.dat");
			
			ObjectInputStream iis = new ObjectInputStream(fis);
			
			tasks = (String[][])iis.readObject();
			
			fis.close();
			
			iis.close();
			
		}catch(Exception e) {
		
		}
			
    	return tasks;
    }
    
    /**
     *  сохранение данных
     */
	public void store(String[][] data) {
		
		try{
			
			FileOutputStream fos = new FileOutputStream("storage.dat");
			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(data);
			
			fos.close();
			
			oos.close();
			
		}catch (Exception e){
		
		}
		
		data = this.tasks;
    }
}



