package com.safetynet.alerts.model;

/**
 * Class of the model used to store the count of children and adults (more than
 * 18 years old).
 *
 * @author Thierry Schreiner
 */
public class CountOfPersons {

    /**
     * The count of adult (>18 years old).
     */
    private long adultCount;
    /**
     * The count of children (18 and less).
     */
    private long childCount;
    /**
     * The total of adults + children.
     */
    private long total;

    /**
     * Empty class constructor.
     */
    public CountOfPersons() {
    }

    /**
     * CountOfPersons class constructor.
     *
     * @param pAdultCount
     * @param pChildCount
     * @param pTotal
     */
    public CountOfPersons(final long pAdultCount, final long pChildCount,
            final long pTotal) {
        this.adultCount = pAdultCount;
        this.childCount = pChildCount;
        this.total = pTotal;
    }

    /**
     * Getter of adultCount (> 18 years old).
     *
     * @return the adultCount
     */
    public long getAdultCount() {
        return adultCount;
    }

    /**
     * Setter of adultCount.
     *
     * @param pAdultCount the adultCount to set
     */
    public void setAdultCount(final long pAdultCount) {
        adultCount = pAdultCount;
    }

    /**
     * Getter of childCount.
     *
     * @return the childCount
     */
    public long getChildCount() {
        return childCount;
    }

    /**
     * Setter of childCount.
     *
     * @param pChildCount the childCount to set
     */
    public void setChildCount(final long pChildCount) {
        childCount = pChildCount;
    }

    /**
     * Getter of total (all persons, adults + children).
     *
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * Setter of total (all persons, adults + children).
     *
     * @param pTotal the total to set
     */
    public void setTotal(final long pTotal) {
        total = pTotal;
    }

}
