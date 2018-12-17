namespace BNB
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Sensor")]
    public partial class Sensor
    {
        [Key]
        [StringLength(50)]
        public string SKey { get; set; }

        [Required]
        [StringLength(150)]
        public string SName { get; set; }

        [Required]
        public string SDetails { get; set; }

        [Required]
        public string SText { get; set; }

        [Required]
        public DateTime? SCreateDate { get; set; }

        [Required]
        public DateTime? SLastChangeDate { get; set; }

        public virtual Author Author { get; set; }

        public virtual Location Location { get; set; }
    }
}
