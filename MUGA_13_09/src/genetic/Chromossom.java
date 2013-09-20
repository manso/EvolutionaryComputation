package genetic;

import genetic.gene.Gene;
import genetic.gene.GeneNumber;
import java.io.Serializable;
import java.util.ArrayList;
import utils.BitField;

/**
 * **************************************************************************
 */
/**
 * ** G E N E T I C A L G O R I T H M S S I M U L A T O R         ***
 */
/**
 * ** ver 1.0 (Sept/2005)                       ***
 */
/**
 * **                                                                     ***
 */
/**
 * ** authors:                                                        ***
 */
/**
 * ** António Manso                                          ***
 */
/**
 * ** URL : http://orion.ipt.pt/~manso/                    ***
 */
/**
 * ** e-mail: manso@ipt.pt                                   ***
 */
/**
 * **                                                                     ***
 */
/**
 * ** Luís Correia                                          ***
 */
/**
 * ** URL : http://www.di.fc.ul.pt/~lcorreia/              ***
 */
/**
 * ** e-mail: Luis.Correia@di.fc.ul.pt                       ***
 */
/**
 * **                                                                     ***
 */
/**
 * **************************************************************************
 */
/**
 * **************************************************************************
 */
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/*
 * Chromossom.java
 * representa um cromossoma como um conjunto de genes
 * Created on 12 de Dezembro de 2004, 15:32
 */
/**
 * representa um cromossoma como um conjunto de genes
 *
 * @author António Manuel Rodrigues Manso
 */
public class Chromossom implements Serializable {

    /**
     * Vector of Genes
     */
    protected ArrayList<Gene> genotype;

    /**
     * Creates a new instance of Chromossom
     */
    public Chromossom() {
        genotype = new ArrayList<>();
    }
    
    public void clear(){
        genotype.clear();
    }

    /**
     * Creates a new instance of Chromossom new chromossom is a copy of template
     *
     * @param cr chromossom template
     */
    public Chromossom(Chromossom cr) {
        genotype = new ArrayList<>(cr.getNumGenes());
        for (int i = 0; i < cr.getNumGenes(); i++) {
            this.addGene((Gene) cr.getGene(i).getClone());
        }
    }

    /**
     * to string
     *
     * @return respresentação em string
     */
    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        for (Gene i : genotype) {
            txt.append(i.toString() + " ");
        }
        return txt.toString();

    }

    public String toStringCSV() {
        StringBuffer txt = new StringBuffer();
        for (Gene i : genotype) {
            txt.append(i.toString() + ", ");
        }
        return txt.toString();

    }

    /**
     * add a clone of the gene
     *
     * @param newGene gene to be cloned
     */
    public void addGene(Gene newGene) {
        genotype.add(newGene);
    }

    /**
     * Atribui valores aleatórios aos genes
     */
    public void fillRandom() {
        for (int i = 0; i < genotype.size(); i++) {
            ((Gene) genotype.get(i)).fillRandom();
        }
    }

    /**
     * substitui um gene do cromossoma
     *
     * @param index numero do cromossoma
     * @param newGene gene a ser adicionado
     */
    public void replaceGene(int index, Gene newGene) {
        genotype.set(index, newGene);
    }

    /**
     * Remove a gene from gentype
     *
     * @param newGene gene to be removed
     */
    public void removeGene(Gene newGene) {
        genotype.remove(newGene);
    }

    /**
     * Remove gene at position index
     *
     * @param index position of gene to be removed
     */
    public Gene removeGeneAt(int index) {
        Gene g1 = (Gene) genotype.get(index);
        genotype.remove(index);
        return g1;
    }

    /**
     * add a clone of the gene at position index
     *
     * @param index position of gene to be removed
     */
    public void addGeneAt(int index, Gene g) {
        genotype.add(index, g.getClone());
    }

    /**
     * returns the gene index of the chromossom
     *
     * @param index index in the chromossom
     * @return Gene at the position index
     */
    public Gene getGene(int index) {
        return (Gene) genotype.get(index);
    }

    /**
     * gets the value of the gene index
     *
     * @param index number of the gene
     * @return value of the gene index
     */
    public double getValue(int index) {
        return ((Gene) genotype.get(index)).getValue();
    }

    /**
     * number of genes int he chromossom
     *
     * @return number of genes
     */
    public int getNumGenes() {
        return genotype.size();
    }

    /**
     * gets the bits of the chromossom
     *
     * @return bits of all genes
     */
    public BitField getBinString() {
        //new bitField
        BitField alels = new BitField();
        //bits of all genes
        for (int i = 0; i < genotype.size(); i++) {
            Gene tmp = (Gene) genotype.get(i);
            alels.appendBits(tmp.getAlels());
        }
        return alels;
    }

    public int getNumberTotalOfBits() {
        int size = 0;
        //bits of all genes
        for (int i = 0; i < genotype.size(); i++) {
            size += genotype.get(i).getNumBits();
        }
        return size;
    }

    /**
     * gets the bits of the chromossom
     *
     * @return bits of all genes
     */
    public boolean getBit(int pos) {
        int gene = 0;
        //calcule the gene of the bit
        while (genotype.get(gene).getNumBits() < pos) {
            pos -= genotype.get(gene).getNumBits();
            gene++;
        }
        return genotype.get(gene).getAlels().getBit(pos);
    }

    /**
     * substitui os bites do cromossoma pelos do parametros
     *
     * @param newBits novos bites do cromossoma
     */
    public void setBinString(BitField newBits) {
        int pos = 0;
        //for all genes
        for (int i = 0; i < genotype.size(); i++) {
            // gene i
            Gene tmp = (Gene) genotype.get(i);
            // bits for the gene i
            BitField bt = newBits.getSubBitField(pos, pos + tmp.getNumBits() - 1);
            tmp.setAlels(bt);
            pos += tmp.getAlels().getNumberOfBits();
        }
    }

    /**
     * [hard-copy] of this
     *
     * @return new instance of Chromossom
     */
    public Chromossom getClone() {
        return new Chromossom(this);
    }

    /**
     * apply mutation at each gene
     *
     * @param probability probability of mutation for every bit
     */
    public void mutate(double probability) {
        for (int index = 0; index < this.getNumGenes(); index++) {
            getGene(index).mutate(probability);
        }
    }

    /**
     * getDistance Chromossoms
     *
     * @param obj obj
     * @return x
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Chromossom) {
            final Chromossom other = (Chromossom) obj;
            if (other.getNumGenes() != this.getNumGenes()) {
                return false;
            }
            for (int index = 0; index < getNumGenes(); index++) {
                if (!this.getGene(index).equals(other.getGene(index))) {
                    return false;
                }
            }
            return true;
        } else {
            return super.equals(obj);
        }
    }

    /**
     * Sets the clone of gene in the position index
     *
     * @param index position to gene
     * @param g gene
     */
    public void setGene(int index, Gene g) {
        genotype.set(index, g.getClone());
    }

    /* hashcod of genotype
     * @return
     */
    @Override
    public int hashCode() {
        return this.genotype.hashCode();
    }

//===================================================================================
//===================================================================================
//===================================================================================
//===================================================================================
//-----------------------------------------------------------------------------------
//                        D I S C R E T I Z A T I O N
//-----------------------------------------------------------------------------------
    public void discretize(long intervals) {
        for (Gene g : genotype) {
            if (g instanceof GeneNumber) {
                ((GeneNumber) g).discretize(intervals);
            }
        }
    }
//===================================================================================
//===================================================================================
//===================================================================================
}
