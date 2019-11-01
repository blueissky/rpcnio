package top.rpc.zk;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZkInit {
//	 private static final String connectionString = "192.168.25.127:2181,"
//	            + "192.168.25.129:2181,"
//	            + "192.168.25.130:2181";
//	    public static final String connectionString = "127.0.0.1:2181";
//	    public static final Integer sessionTimeout = 2000;
//		public String connectionString = "127.0.0.1:2181";
//		public Integer sessionTimeout = 2000;
//		public String path="/maple";
//		public String pathValue="123467";
	    private  ZooKeeper zkClient = null;
		public ZkInit(String connectionString,Integer sessionTimeout,String zkpath,String pathValue) {
			// TODO Auto-generated constructor stub
//			connectionString = this.connectionString;
//		    sessionTimeout = this.sessionTimeout;
//		    path=this.path;
//			pathValue=this.pathValue;
			try {
				this.run(connectionString,sessionTimeout,zkpath,pathValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	    
	    public ZooKeeper init(String connectionString,Integer sessionTimeout) throws Exception{
	    //三个参数分别为连接的zookeeper集群服务器的ip，超时时间，监听器
	        zkClient = new ZooKeeper(connectionString, sessionTimeout, new Watcher(){
	            //收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
	            public void process(WatchedEvent event) {
	                System.out.println(event.getType()+","+event.getPath());
	            }});
	        return zkClient;
	    }
	    
	    public void getChildren()  throws Exception{
	        /*
	         * 传入2个参数
	         * 1、指定获取哪个节点的孩子
	         * 2、是否使用监听器(watcher)，true表示使用以上的监听功能
	         */
	        List<String> children = zkClient.getChildren("/",true);
	        for (String child : children) {
	            System.out.println(child);
	        }
	    }
// 	    //增
//	    public static void create(String path,String server) throws Exception{
//	    	zkClient.create(path,server.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//	    }
//	    //删
//	    public static void delete(String path) throws Exception{	
//	    	zkClient.delete(path, stat.getVersion());
//	    }
	    public void deleteAll(String path,Stat stat) {
	    	try {
				zkClient.delete(path, stat.getVersion());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
//	    //改
	    public void setData(String path,String server,Stat stat) throws Exception{
	    	System.out.println(path);
	    	zkClient.setData(path, server.getBytes(), stat.getVersion());
		}
	    //查
	    public  void getData(String serverPath) throws Exception{
	    	byte[] data = zkClient.getData(serverPath,false, null);
	    	String val = new String(data,"UTF-8");
	    	System.out.println(val);
	    }
	    
	    public void run(String connectionString,Integer sessionTimeout,String path,String pathValue) throws Exception{
	    	ZooKeeper client = this.init(connectionString,sessionTimeout);

			//step1 判断存不存在
			
			//step2 存在，更新
            //       不存在，创建保存
			
			Stat stat = zkClient.exists(path, false);
			if(stat==null) {//不存在
				System.out.println("不存在");
				client.create(path,pathValue.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}else {//存在
				System.out.println("存在");
				client.setData(path, pathValue.getBytes(), stat.getVersion());
			}
			
			getData(path);
			zkClient.close();
	    }
	    
//	    public static void main(String[] args) {
//			try {
//				String connectionString = "127.0.0.1:2181";
//			    Integer sessionTimeout = 2000;
//			    String path="/maple";
//				String pathValue="123467";
//				ZKInit zk=new ZKInit();
//				ZooKeeper client = zk.init(connectionString,sessionTimeout);
//
//				//step1 判断存不存在
//				
//				//step2 存在，更新
//                //       不存在，创建保存
//				
//				Stat stat = zkClient.exists(path, false);
//				if(stat==null) {//不存在
//					System.out.println("不存在");
//					client.create(path,pathValue.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//				}else {//存在
//					System.out.println("存在");
//					client.setData(path, pathValue.getBytes(), stat.getVersion());
//				}
//				
//				getData(path);
//				
//			 // 获取节点
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
}
