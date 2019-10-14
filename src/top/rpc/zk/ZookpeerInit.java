package top.rpc.zk;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookpeerInit{

 
    private static ZooKeeper zk;
	private static String serverPath="/server";
	private static Stat stat;
	private String path;
	private String server;
	private int timeout;
	private String monitorServer;
	
	public ZookpeerInit(String path,String server,int timeout,String monitorServer) {
		// TODO Auto-generated constructor stub
		this.path=path;
		this.server=server;
		this.timeout=timeout;
		this.monitorServer=monitorServer;
		try {
			this.init(server,path,timeout,monitorServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init(String server,String path,int timeout,String monitorServer) throws Exception{
		Watcher watcher = new Watcher(){
			public void process(WatchedEvent event) {
				System.out.println(event.toString());
			}
		};
		//连接服务
		zk = new ZooKeeper(server,timeout, watcher);
		//当前节点是否存在
		stat = zk.exists(path, watcher);
		if(stat==null){
			create(path,monitorServer);
			System.out.println("create");
		}else{
			System.out.println(monitorServer);
			setData(path,monitorServer);
			System.out.println("setData");
		}
	}
	
	//增
    public static void create(String path,String server) throws Exception{
		zk.create(serverPath,server.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    //删
    public static void delete() throws Exception{
    	zk.delete(serverPath, stat.getVersion());
    }
    //改
    public static void setData(String path,String server) throws Exception{
		zk.setData(path, server.getBytes(), stat.getVersion());
	}
    //查
    public static void getData() throws Exception{
    	byte[] data = zk.getData(serverPath,false, stat);
    	String val = new String(data,"UTF-8");
    	System.out.println(val);
    }
}
