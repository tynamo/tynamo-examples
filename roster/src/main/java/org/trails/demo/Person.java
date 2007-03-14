package org.trails.demo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.NotNull;
import org.trails.descriptor.BlobDescriptorExtension.ContentDisposition;
import org.trails.descriptor.BlobDescriptorExtension.RenderType;
import org.trails.descriptor.annotation.BlobDescriptor;
import org.trails.descriptor.annotation.ClassDescriptor;
import org.trails.descriptor.annotation.PropertyDescriptor;
import org.trails.util.DatePattern;

/**
 * A Person has a photo, eRole and application role
 *
 * @author kenneth.colassi        nhhockeyplayer@hotmail.com
 */
@MappedSuperclass
@ClassDescriptor(hidden = true)
public class Person implements Serializable {
    private static final Log log = LogFactory.getLog(Person.class);

    protected enum ERole {
        USER, ADMIN
    }

    protected enum ApplicationRole {
        MANAGER, DIRECTOR, HEADCOACH, ASSISTANTCOACH, EQUIPMENTMGR, OPERATIONS, TRAINER, SALES, MARKETING, PLAYER
    }

    protected Integer id = null;

    protected String firstName;

    protected String lastName;

    protected Date dob;

    protected String emailAddress;

    protected String password;

    protected ERole eRole;

    protected ApplicationRole applicationRole;

    protected Long created = new Long(GregorianCalendar.getInstance()
            .getTimeInMillis());

    protected Long accessed = new Long(GregorianCalendar.getInstance()
            .getTimeInMillis());

    /**
     * CTOR
     */
    public Person(Person dto) {
        try {
            BeanUtils.copyProperties(this, dto);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    public Person() {
    }

    /**
     * Accessor for id
     *
     * @return Integer
     * @hibernate.id generator-class="increment" unsaved-value="-1"
     *               type="java.lang.Integer" unique="true" insert="true"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(readOnly = true, summary = true, index = 0)
    public Integer getId() {
        return id;
    }

    private UploadableMedia photo = new UploadableMedia();

    @BlobDescriptor(renderType = RenderType.IMAGE, contentDisposition = ContentDisposition.ATTACHMENT)
    @PropertyDescriptor(summary = true, index = 1)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public UploadableMedia getPhoto() {
        return photo;
    }

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "is required")
    public ERole getERole() {
        return eRole;
    }

    @PropertyDescriptor(summary = true, index = 3)
    public String getFirstName() {
        return firstName;
    }

    @PropertyDescriptor(summary = true, index = 2)
    public String getLastName() {
        return lastName;
    }

    public Date getDob() {
        return dob;
    }

    @Column(unique = true)
    @PrimaryKeyJoinColumn
    @PropertyDescriptor(summary = true, index = 4)
    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "is required")
    public ApplicationRole getApplicationRole() {
        return applicationRole;
    }

    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public Long getCreated() {
        return created;
    }

    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public Long getAccessed() {
        return accessed;
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public String getCreatedAsString() {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(created.longValue());
        return DatePattern.sdf.format(cal.getTime());
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public String getAccessedAsString() {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(accessed.longValue());
        return DatePattern.sdf.format(cal.getTime());
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public void setCreatedAsString(String value) throws Exception {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(DatePattern.sdf.parse(value).getTime());
        this.created = new Long(cal.getTimeInMillis());
    }

    @Transient
    @PropertyDescriptor(hidden = true, summary = false, searchable = false)
    public void setAccessedAsString(String value) throws Exception {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(DatePattern.sdf.parse(value).getTime());
        this.accessed = new Long(cal.getTimeInMillis());
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPhoto(UploadableMedia photo) {
        this.photo = photo;
    }

    public void setERole(ERole role) {
        this.eRole = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApplicationRole(ApplicationRole applicationRole) {
        this.applicationRole = applicationRole;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public void setAccessed(Long accessed) {
        this.accessed = accessed;
    }

    @Override
    public Person clone() {
        return new Person(this);
    }

    @Override
    public String toString() {
        return getLastName() + ", " + getFirstName();
    }

    /*
     * public boolean equals(Object obj) { // return
     * EqualsBuilder.reflectionEquals(this, obj); if (this == obj) return true;
     * if (obj == null || this.getClass() != obj.getClass()) return false;
     *
     * final Person castedObj = (Person) obj;
     *
     * return this.getId().equals(castedObj.getId()); }
     */

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs)
            return true;
        if (rhs == null)
            return false;
        if (!(rhs instanceof Person))
            return false;
        final Person castedObject = (Person) rhs;
        if (getId() == null) {
            if (castedObject.getId() != null)
                return false;
        } else if (!getId().equals(castedObject.getId()))
            return false;
        return true;
    }
}