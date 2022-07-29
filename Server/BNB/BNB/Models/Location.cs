namespace BNB
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Location")]
    public partial class Location
    {
        [Key]
        [StringLength(50)]
        public string LKey { get; set; }

        [Required]
        [StringLength(50)]
        public string LCity { get; set; }

        [Required]
        [StringLength(50)]
        public string LCountry { get; set; }

        [Required]
        [StringLength(150)]
        public string LFromMap { get; set; }

        [Required]
        [StringLength(150)]
        public string LCoordFromMap { get; set; }

        public virtual Sensor Sensor { get; set; }
    }
}
