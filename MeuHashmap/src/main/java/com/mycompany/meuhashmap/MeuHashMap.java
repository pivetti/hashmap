package com.mycompany.meuhashmap;

public class MeuHashMap<K, V> {
    
    private static final float LOAD_FACTOR = 0.75f;

    //1. Array de entradas (Buckets/baldes)
    //Armazena a "cabeça" da lista de cada índice
    private Entry<K, V>[] table;
    
    //2. Capacidade inicial
    private static final int INITIAL_CAPACITY = 16;
    
    //3. Contador do número de elementos
    private int size = 0;
    
    //Construtor
    public MeuHashMap() {
        table = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
    }
    
    private int getIndex(K key){
        //O método hashCode() herdado da Object (Classe do Java) 
        //passamos um objeto e ele vai retornar um número inteiro
        int hash = key.hashCode();
        
        //Aplicando o módulo para estar dentro do array
        //Math.abs garante um número positivo (valor absoluto)
        //o operador "%" retorna o resto da divisão, sempre entre 0 e table.length
        return Math.abs(hash) % table.length;
    }
    
    public void put(K key, V value){
        //conferindo se a chave é nula
        if(key == null) return;
        
        if((1.0 * size) / table.length >= LOAD_FACTOR){
            resize();
        }
        
        int index = getIndex(key);
        Entry<K, V> newEntry = new Entry<>(key, value);
        
        //inserindo no índice caso tabela esteja vazia
        if(table[index] == null){
            table[index] = newEntry;
            size++;
        } else {
            //quando ocorre colisão
            Entry<K, V> current = table[index];
            Entry<K, V> previous = null;
            
            while(current != null){
                //se encontrar a CHAVE existente (usamos equals() para isso)
                if(current.key.equals(key)){
                    //atualizamos o valor
                    current.value = value;
                    return; //sai do método após atualizar
                }
                previous = current;
                current = current.next;
            }
            //Chave não encontrada
            previous.next = newEntry;
            size++;
        }
    }
    
    //metodo get recupera o valor atrelado a uma chave
    public V get(K key){
        if(key == null) return null;
        
        int index = getIndex(key);
        Entry<K, V> current = table[index];
        
        //percorrer a lista encadeada desse índice (balde)
        while(current != null){
            if(current.key.equals(key)){
                return current.value;
            }
            current = current.next;
        }
        //chave não encontrada no índice
        return null;
    }
    
    //metodo que retorna o tamanho do hashmap
    public int size(){
        return size;
    }
    
    public void printIndexData(int index){
        //validar se indice não esta fora da tabela
        if(index < 0 || index >= table.length){
            System.out.println("Indice invalido: " + index);
            return;
        }
        
        System.out.println("Conteudo do indice[" + index + "]");
        
        //Pega o primeiro elemento (cabeça da lista desse índice)
        Entry<K, V> current = table[index];
        
        if(current == null) {
            //indice vazio
            System.out.println("Vazio(nao houve colisoes aqui)");
        } else {
            //indice que possui elementos
            StringBuilder sb = new StringBuilder();
            int count = 0;
            
            //Percorre a lista encadeada até o fim (até o próximo elemento ser null)
            while(current != null){
                //imprime chave-valor
                sb.append("(").append(current.key).append("=").append(current.value).append(")");
                
                //adiciona seta indicando o encadeamento se houver próximo
                if(current.next != null){
                    sb.append("->");
                }
                //vai para o próximo elemento da lista
                current = current.next;
                count++;
            }
            
            System.out.println(sb.toString());
            System.out.println("\n\nTotal de elementos neste indice (colisoes): " + count);
        }
    }
    
    //metodo para percorrer a estrutura inteira
    public void printStructure(){
        System.out.println("\n\nEstrutura do MeuHashMap");
        for(int i = 0; i < table.length; i++){
            System.out.print("Indice["+i+"]: ");
            Entry<K, V> current = table[i];
            if(current == null){
                System.out.println("Vazio");
            } else {
               StringBuilder sb = new StringBuilder();
               while(current != null){
                   sb.append(current.key).append(": ").append(current.value).append(" -> ");
                   current = current.next;
               }
               sb.append("NULL");
                System.out.println(sb.toString());
            }
        }
    }
    
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[oldTable.length * 2];
        size = 0;

        for(Entry<K, V> entry : oldTable){
            while(entry != null){
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }
    
    public V remove(K key) {
        int index = getIndex(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while(current != null){
            if(current.key.equals(key)){
                if(previous == null){
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null; // chave não encontrada
    }
    
    public double calculateAverage(){
        double sum = 0.0;
        int count = 0;
        
        for(int i = 0; i < table.length; i++){
            Entry<K, V> current = table[i];
            while(current != null){
                sum += (Double) current.value;
                count++;
                current = current.next;
            }
        }
        
        if(count <= 0) return 0.0;
        return sum / count;
    }

    public static void main(String[] args) {
        MeuHashMap<String, Double> studentGrades = new MeuHashMap<>();
        
        // Inserindo elementos (nome do aluno -> nota)
        studentGrades.put("Ana", 9.2);
        studentGrades.put("Bruno", 8.5);
        studentGrades.put("Carla", 7.8);
        studentGrades.put("Diego", 8.8);
        studentGrades.put("Eduarda", 9.5);
        studentGrades.put("Felipe", 7.3);
        studentGrades.put("Gabriela", 8.1);
        studentGrades.put("Henrique", 9.0);
        studentGrades.put("Isabela", 9.5);
        studentGrades.put("João", 6.9);
        studentGrades.put("Marcos", 7.6);
        studentGrades.put("Natália", 8.4);
        studentGrades.put("Otávio", 9.1);
        studentGrades.put("Paula", 7.7);
        studentGrades.put("Rafael", 8.9);
        
        //atualizando um valor existente
        studentGrades.put("Henrique", 10.0);
        
        //buscando valores
        System.out.println("\nNota de Henrique: " + studentGrades.get("Henrique"));
        System.out.println("Nota de Isabela: " + studentGrades.get("Isabela"));
        
        //exibindo tamanho do mapa
        System.out.println("\nTotal de registros: " + studentGrades.size());
        
        //exibir conteúdo de um índice específico
        studentGrades.printIndexData(2);
        
        //exibir estrutura completa
        studentGrades.printStructure();
        
        System.out.printf("\nA média das notas é: %.2f%n", studentGrades.calculateAverage());
        
        //remover um elemento
        studentGrades.remove("João");
        System.out.println("\nApós remover João:");
        studentGrades.printStructure();
        
        System.out.printf("\nA média das notas é: %.2f%n", studentGrades.calculateAverage());
    }
}
