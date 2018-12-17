namespace BNB
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Author")]
    public partial class Author
    {
        [Key]
        [StringLength(50)]
        public string AKey { get; set; }

        [Required]
        [StringLength(60)]
        public string AName { get; set; }

        [StringLength(20)]
        public string APhone { get; set; }

        [Required]
        [StringLength(30)]
        public string AEmail { get; set; }

        public virtual Sensor Sensor { get; set; }

    }
}
