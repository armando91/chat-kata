package chat.kata

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock

class ChatService {

    private ReentrantReadWriteLock _rwl = new ReentrantReadWriteLock()
    private Lock _r_lock = _rwl.readLock()
    private Lock _w_lock = _rwl.writeLock()
    private Collection<ChatMessage> _myCollection = new ArrayList<ChatMessage>()

    /**
     * Collects chat messages in the provided collection
     * 
     * @param if specified messages are collected from the provided sequence (exclusive)
     * @param messages the collection where to add collected messages
     * 
     * @return the sequence of the last message collected.
     */
    Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
        _r_lock.lock()        
        Integer value = fromSeq == null ? 0 : fromSeq + 1
        Integer size = _myCollection.size()
        while(value < size){
            collector.add(_myCollection.getAt(value++));
        }
        _r_lock.unlock()
        return value -1
    }

    /**
     * Puts a new message at the bottom of the chat
     * 
     * @param message the message to add to the chat
     */
    void putChatMessage(ChatMessage message){
        _w_lock.lock()
        if(message != null){
            _myCollection.add(message);
        }
        _w_lock.unlock()
    }
}