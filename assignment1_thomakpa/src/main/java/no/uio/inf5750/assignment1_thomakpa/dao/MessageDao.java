package no.uio.inf5750.assignment1_thomakpa.dao;



import no.uio.inf5750.assignment1_thomakpa.model.Message;



public interface MessageDao

{

   int save( Message message );

   Message get( int id );

   Message getLast();    

}