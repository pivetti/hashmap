/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meuhashmap;

/**
 *
 * @author Aluno
 */
public class Entry<K, V> {
    K key;
    V value;
    
    //Ponteiro para o proximo da lista(colisao)
    Entry<K, V>next;
    
    public Entry(K key,V value){
        this.key = key;
        this.value = value;
        this.next = null;
    }
    
}