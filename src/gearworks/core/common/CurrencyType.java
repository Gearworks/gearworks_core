package gearworks.core.common;

import org.bukkit.Material;

public enum CurrencyType {

    COINS("Coins", Material.GOLD_INGOT),
    GEMS("Gems", Material.DIAMOND);

    private String currencyName;
    private Material currencyType;

    private CurrencyType (String currencyName, Material currencyType){
        this.currencyName = currencyName;
        this.currencyType = currencyType;
    }

    public String getCurrencyName (){
        return currencyName;
    }

    public Material getCurrencyType (){
        return currencyType;
    }
}
