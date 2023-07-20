package CommonComponents;

public class CommonConstants {

    public static final String HS_USERNAME = "hs.username";
    public static final String HS_PASSWORD = "hs.password";
    public static final String HS_WEBURL = "hs.weburl";
    public enum PROJECT
    {
        HS, LD, PS, RSB, SSN, common;
    }
    public enum PROPERTY_TYPE
    {
        OFFICE("Office Space"),
        COWORKING("Co-Working"),
        SHOP("Shop"),
        SHOWROOM("Showroom"),
        WAREHOUSE("Godown/Warehouse"),
        BUILDING("Industrial Building"),
        RESTAURANT("Restaurant/Cafe"),
        OTHER("Other business"),
        SHED("Industrial Shed");
        String value;

        PROPERTY_TYPE(String s)
        {
            value = s;
        }

        public String getValue()
        {
            return value;
        }
    }
    public enum STAMP_SELECTION
    {
        STAMP100("₹ 100 Stamp"),
        STAMP50("₹ 50 Stamp"),
        STAMP20("₹ 20 Stamp"),
        STAMP200("₹ 200 Stamp"),
        STAMP300("₹ 300 Stamp"),
        STAMP400("₹ 400 Stamp"),
        STAMP500("₹ 500 Stamp");

        String value;

        STAMP_SELECTION(String s)
        {
            value = s;
        }
        public String getValue()
        {
            return value;
        }
    }
    public enum GENDER
    {
        Male, Female
    }

    public enum RA_VALUES
    {
        ESTAMPEDAGREEMNT("E-Stamped Agreement"),UPLOADYOURDRAFT("Upload your Draft"),

        rentalAgreement("Rental Agreement"),raPlusPoliceIntimation("Rental Agreement + Police Intimation"),
        LegalService("Legal Services"),notary("5f3a9def6ed0974167ab1667"),eSign_Agreement("62c6dddee771577b5113730e"),
        Stamp200("200 Stamp"),Stamp100(" 100 Stamp"),Stamp500("500 Stamp"), Locality("Locality"),STAMP100("₹ 100 Stamp"),STAMP500("₹ 500 Stamp"),
        SalesDashboardMenu("Legal Document Rental Sales Dashboard"), SalesDashboardTitle("Legal Services Dashboard (Sales)"), AddLead("Add  Lead"), FinalizeDraft("Finalize Draft"), ScheduleBiometric("Schedule Biometric"),
        RentalAgreement("Rental Agreement"), PoliceIntimation("Police Intimation"), CheckPrices("Check Prices"), CreateRentalAgreement("Create Rental Agreement"), Myself("No, I will do it Myself"), BiometricJobCompleted("Biometric Job Completed"), PropertyDetail("Property Detail"),
        ContinueasTenant("Continue as Tenant"), SkipContinue("Skip & Continue"), Home, Fixed, SendPaymentLink("Send Payment Link"), SendInspectinPaymentLink("Save & Send Inspection Payment Link"), ViewEditDoc("View / Edit Document"), MaintenancePaidBy("Maintenance Paid By"), FollowUpReason("NC/Busy/Callback"),
        ExecutingThrough("Executing Through"), LandlordDetail("Landlord Detail"), TenantDetail("Tenant Detail"), ContractDetail("Contract Detail"), MinimumLockinPeriod("Minimum Lockin Period (In months)"), EditOrder("Edit Order"), agreement_id("Agreement Id"),
        BiometricAppointments("Biometric Appointments"), AddNewAppointment("Add New Appointment"), UploadDocuments("Upload Documents"), ShareCopy("Share Registered Soft Copy"), BiometricVerification("Biometric Verification"), user_id,

        Owner, Tenant, flat("Apartment/Flat"), IndependentHouse("Independent House"), city, Location,

        Months10("10 Months"),Months11("11 Months"), AMT1K("1000"), AMT8K("8,000"), AMT9K("9,000"), AMT9k("9000"), AMT10K("10,000"), AMT11K("11,000"), AMT12K("12000"),

        TypeofProperty("Type of Property"), BuildingName("Building Name"), FlatHouseNumber("Flat Number / House Number"), FlatNumber("Flat Number"), FloorNumber("Floor Number"), HouseNumber("House Number"), PermanentAddressFull("Permanent Address Full"), TypeofUnit("Type of Unit"),
        PropertyAddressFull("Property Address (Full)"), Pincode2("Pincode"), FlatHouseBuiltUpArea("Flat/House Built-up Area"), PropertyNumberType("Property Number Type"), RoadStreet("Road / Street"), PropertyNumber("Property Number"), BuiltupAreaUnit("Built-up Area Unit"),
        IndependentHouseNumber("Independent House Number"),

        RentAmt("Rent Amount"),MonthlyRentAmt("Monthly Rent Amount"), RefundableDeposit("Refundable Deposit"),RefundableDepositamt("Refundable Deposit Amount"), NonRefundableDeposit("Non Refundable Deposit"), BranchName("Branch Name"), PINCode("PIN Code"), District("District"), VillageCity("Village/City"), Taluka("Taluka"),
        ChequeNumber("Cheque Number"), ChequeAmount("Cheque Amount"), RentDay("Enter Rent Day"), NoticePeriod("Notice Period (In months)"), AgreementDuration("Agreement Duration"),AGREEMENTDURATION("Agreement Duration (In months)"),DateofCheque("Date of Cheque"), CashAmount("Cash Amount"), TransferAmt("Transfer Amount"), DateofTransferAmt("Date of Transfer Amount"),
        DepositPaymentMode("Deposit Payment Mode"), BankName("Bank Name"), StampPaidBy("Stamp Duty & Registration Fee Paid By"), AgreementStartDate("Agreement Start Date"), ChequeNEFT("Cheque/NEFT/RTGS"), TxnRefNumber("Transaction Reference Number"), DemandDraft("Demand Draft"),

        BedroomNumber("Number Of Bedrooms"), BathroomNumber("Number of Bathrooms"),
        FansNumber("Number of Fans"), TubesNumber("Number of Tubes"), BedsNumber("Number of Beds"),

        FullName("Full Name"), Age, AadharNumber("Aadhar Number (12 Digits)"), PAN("PAN Number (ABCDE1234F)"), EmailAddress("Email Address"), Occupation, Business, Service, Licensor, Licensee, Salutation,
        MiddleName("Middle Name"), LastName("Last Name"), FirstName("First Name"), Surname, Preview,Phone("Phone"),


        BlrAddress1("Koramangala, Bengaluru, Karnataka, India"), BlrAddress2("Sanjaynagar, Bengaluru, Karnataka, India"), BlrAddress3("Malleshwaram, Bengaluru, Karnataka, India"), BankAxis("AXIS BANK LTD"), IDBI,
        MbiAddress1("Juhu, Mumbai, Maharashtra, India"), MbiPin1("400049"), MbiAddress2("Worli, Mumbai, Maharashtra, India"), MbiPin2("40,0018"), MhState("Maharashtra"), PuneAddress1("Pune, Hadapsar, Maharashtra"),


        ccBiometricAvailable("Can all landlords and tenants be available at one location in Mumbai/Pune for biometric verification?"),
        ccBiometricPartyIndia("Do all parties stay within India?"),
        PartyType("Party Type"), Gender("Gender");


        String value;

        RA_VALUES(String s)
        {
            value = s;
        }

        RA_VALUES()
        {
            value = this.name();
        }

        public String toString()
        {
            return value;
        }
    }

    public enum CITY_SELECTION
    {
        MUMBAI("Mumbai"),
        BANGALORE("Bangalore"),
        PUNE("Pune"),
        CHENNAI("Chennai"),
        GURGAON("Gurgaon"),
        HYDERABAD("Hyderabad"),
        DELHI("Delhi"),
        NOIDA("Noida"),
        GREATER_NOIDA("Greater Noida"),
        GHAZIABAD("Ghaziabad"),
        FARIDABAD("Faridabad"),
        THANE("Thane, Maharashtra, India");
        String value;

        CITY_SELECTION(String s)
        {
            value = s;
        }

        public String getValue()
        {
            return value;
        }
    }

}
