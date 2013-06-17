package com.company;

public class Storage {
	 
    /**
     * Двумерный массив с задачами.
     * Ключ записи - <i> задачи.
     * Поля записи - <a> старта и финиша, имя задачи
     */
    public String[][] tasks;
 
    
    public Storage() {
        // конструктор
    	
		this.tasks = new String[100][];
		
		this.tasks[0] = new String[]{"Название задачи", "2013-10-10 11:12:13", "2013-10-10 11:12:14"};
    }
    
 
    public String[] add(String[] task) {
        // добавление таска
    	
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
    
 
    public String[] get(Integer id) {
        // вохвращает задачу по айдишнику
    	
    	String[][] data = getTasks();
    	
        return  data[id];
    }
    
    
    public int getIdByName(String name) {
        // вохвращает id задачи по имени
    	String[][] data = getTasks();
    	
    	for(int i = 0; i < data.length; i++)
    	{
    		if(data[i][0] == name)
    			return i;
    	}
    	
        return -1;
    }
    
 
    public boolean delete(Integer id) {
        // удаление таска по айдишнику
    	
    	String[][] data = getTasks();
    	
    	tasks[id] = new String[]{};
    	
    	store(data);
    	
        return true;
    }
    
 
    public boolean truncate() {
        // удаление всех задач
    	
    	store(new String[100][]);
    	
        return true;
    }
    
    
    private String[][] getTasks() {
    	//получение данных
    	
    	return tasks;
    }
    
	
	public void store(String[][] data) {
		//сохранение данных
		
		data = this.tasks;
    }
}


