import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

public class AddressCacheImplementorTest {
	AddressCacheImplementor address_cache = null;
	
	@Before
    public void setUp() {
		address_cache = new AddressCacheImplementor(5);
    }
	
	@Test
	public void offerAddsNewAddessToFrontOfList() throws UnknownHostException {

		InetAddress inet_address = InetAddress.getByName("123.0.0.1");
		
		address_cache.offer(inet_address);
		
		assertTrue("inet_address will be at the top of the list", address_cache.peek() == inet_address);	
	}
	
	@Test
	public void TestOfferWithMultipleAddresses() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		assertTrue("first_inet_address will be at the top of the list",address_cache.peek() == first_inet_address);
		
		address_cache.offer(second_inet_address);
		assertTrue("second_inet_address will now be at the top of the list",address_cache.peek() == second_inet_address);	
	}
	
	@Test
	public void TestIfAddressInCacheDeleteLastOccurenceAndAddToFrontOfList() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		address_cache.offer(second_inet_address);
		address_cache.offer(first_inet_address);
		
		assertTrue("make sure that first_inet_address is fist element in cache",address_cache.peek() == first_inet_address);
		assertTrue("assert that the size of the cache is still 2, not 3",address_cache.size() == 2);	
	}
	
	@Test
	public void ContainsReturnsTrueIfAddressInCache() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		
		address_cache.offer(first_inet_address);
		
		assertTrue(address_cache.contains(first_inet_address));
	}
	
	@Test
	public void ContainsReturnsFalseIfAddressNotInCache() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		
		
		assertFalse("second_inet_address was not added to cache", address_cache.contains(second_inet_address));
	}
	
	
	@Test
	public void RemoveReturnsTrueIfAddressInCache() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		
		address_cache.offer(first_inet_address);
		
		assertTrue("first_inet_address address was remove", address_cache.remove(first_inet_address));
		assertTrue("size of cache is 0", address_cache.size() == 0);
	}
	
	@Test
	public void RemoveReturnsFalseIfAddressNotInCache() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		
		assertFalse("second_inet_address not in cache", address_cache.remove(second_inet_address));
		assertTrue("size of cache is still 1 and nothing was removed", address_cache.size() == 1);
	}
	
	@Test
	public void PeekReturnsMostRecentlyAddedAddressToQueue() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		assertTrue("top address is first_inet_address", address_cache.peek() == first_inet_address);
		
		address_cache.offer(second_inet_address);
		assertTrue("top address is second_inet_address", address_cache.peek() == second_inet_address);
	}
	
	@Test
	public void IfListisEmptyPeekReutrnsNull() throws UnknownHostException {
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		
		assertNull("method should return Null since if list is empty", address_cache.peek());
	}
	
	@Test
	public void RemoveReturnsTheTopAddressInCache() throws UnknownHostException{
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		address_cache.offer(second_inet_address);
		
		assertTrue("should return second_inet_address", address_cache.remove() == second_inet_address);
		assertTrue("should return second_inet_address", address_cache.remove() == first_inet_address);
	}
	
	@Test
	public void RemoveReturnsNullIfCacheIsEmpty() throws UnknownHostException{
		assertNull("method should return null", address_cache.remove());
	}
	
	@Test
	public void TestSize() throws UnknownHostException{
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		address_cache.offer(second_inet_address);
		
		assertTrue("size should be 2", address_cache.size() == 2);
	}
	
	@Test
	public void CloseCache() throws UnknownHostException{
		InetAddress first_inet_address = InetAddress.getByName("123.0.0.1");
		InetAddress second_inet_address = InetAddress.getByName("124.0.0.1");
		
		address_cache.offer(first_inet_address);
		address_cache.offer(second_inet_address);
		
		address_cache.close();
		
		assertTrue("size of cache should be 0", address_cache.size() == 0);
		
		address_cache.offer(first_inet_address);
		assertTrue("cache should no accept any new addresses", address_cache.size() == 0);
	}
	
}
